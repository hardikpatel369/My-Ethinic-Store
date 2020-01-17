package com.example.myethinicstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String CategoryName, Description, Price, ProductName, saveCurrentDate, saveCurrentTime;
    private String ProductRendomKey, downloadImageUrl;
    private StorageReference ProductImageRef;
    private static final int GallaryPick = 1;
    private Uri ImageUri;
    private DatabaseReference ProductRef;

    private ImageView ivSelectImage;
    private EditText etProductName, etProductDescription, etProductPrice;
    private Button btnAddNewProduct;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        CategoryName = getIntent().getExtras().get("category").toString();
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");

       ivSelectImage = findViewById(R.id.ivSelectImage);
       etProductName = findViewById(R.id.etProductName);
       etProductDescription = findViewById(R.id.etProductDescription);
       etProductPrice = findViewById(R.id.etProductPrice);
       btnAddNewProduct = findViewById(R.id.btnAddNewProduct);
       loadingBar = new ProgressDialog(this);


       ivSelectImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               OpenGallary();
           }
       });

       btnAddNewProduct.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ValidateProductData();
           }
       });
    }

    private void ValidateProductData()
    {
        Description = etProductDescription.getText().toString();
        Price = etProductPrice.getText().toString();
        ProductName = etProductName.getText().toString();

        if(ImageUri == null)
        {
            Toast.makeText(this, "Product image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(ProductName))
        {
            Toast.makeText(this, "Please write product Name...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation()
    {
        loadingBar.setTitle("Adding New Product");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new product...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM d,EEE,yyyy");
        saveCurrentDate = currentDate.format(Calendar.getInstance().getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        ProductRendomKey = saveCurrentDate + " " +saveCurrentTime;

        final StorageReference filePath = ProductImageRef.child(ImageUri.getLastPathSegment() + ProductRendomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                Toast.makeText(AdminAddNewProductActivity.this, "ERROR :" + message, Toast.LENGTH_LONG).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(AdminAddNewProductActivity.this, "Image uploaded succesfully.", Toast.LENGTH_SHORT).show();

                Task<Uri>  urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        
                        if(task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "got the product image url successfully.", Toast.LENGTH_SHORT).show();

                            SaveProductInfoTODatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfoTODatabase()
    {
        HashMap<String,Object> productMap = new HashMap<>();
        productMap.put("pid",ProductRendomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",Description);
        productMap.put("image",downloadImageUrl);
        productMap.put("category",CategoryName);
        productMap.put("price",Price);
        productMap.put("pname",ProductName);

        ProductRef.child(ProductRendomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(AdminAddNewProductActivity.this,AdminCategoryActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewProductActivity.this, "Product is added successfully ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "ERROR : " + message, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void OpenGallary()
    {
        Intent gallaryIntent = new Intent();
        gallaryIntent.setAction(Intent.ACTION_GET_CONTENT);
        gallaryIntent.setType("image/*");
        startActivityForResult(gallaryIntent,GallaryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GallaryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            ivSelectImage.setImageURI(ImageUri);
        }
    }
}
