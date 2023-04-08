package com.inficta.alokitsupport.Act;

import static com.inficta.alokitsupport.Base.AppConstants.USER_ID;
import static com.inficta.alokitsupport.Base.AppConstants.YEAR_ID;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;

import androidx.lifecycle.Observer;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.inficta.alokitsupport.Adapter.CustomSpinnerSelectCompany;
import com.inficta.alokitsupport.Adapter.CustomSpinnerSelectLeaveType;
import com.inficta.alokitsupport.Attendance.Attendance;
import com.inficta.alokitsupport.Base.BaseActivity;
import com.inficta.alokitsupport.Base.Preferences;
import com.inficta.alokitsupport.Model.CompanyModel;
import com.inficta.alokitsupport.Model.LeaveListModel;
import com.inficta.alokitsupport.Model.LeaveModel;
import com.inficta.alokitsupport.R;
import com.inficta.alokitsupport.Vm.AttendanceVm;
import com.inficta.alokitsupport.Vm.CounterVm;
import com.inficta.alokitsupport.databinding.ActivityApplyLeaveBinding;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ApplyLeave extends BaseActivity implements View.OnClickListener {
    AttendanceVm attendanceVm;

    CounterVm counterVm;
    ActivityApplyLeaveBinding al;
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar1 = Calendar.getInstance();
    Preferences preferences;
    int CompanyId, Leave_Type;
    public static final String KEY_User_Document1 = "";
    private String Document_img1 = "";
    private ArrayList<String> mResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_leave);

        al = ActivityApplyLeaveBinding.inflate(getLayoutInflater());
        setContentView(al.getRoot());


        preferences = new Preferences(this);
        attendanceVm = new AttendanceVm(this.getApplication());
        counterVm = new CounterVm(this.getApplication());

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                al.etFromDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        al.aReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ApplyLeave.this,PreviousLeave.class));
            }
        });

        al.etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ApplyLeave.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                al.etToDate.setText(sdf.format(myCalendar1.getTime()));
            }
        };

        al.etToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ApplyLeave.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        al.spnCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                CompanyModel.Datum selectedItem = (CompanyModel.Datum) adapterView.getSelectedItem();
                CompanyId = Integer.parseInt(selectedItem.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        attendanceVm.getCompany().observe(ApplyLeave.this, new Observer<CompanyModel>() {
            @Override
            public void onChanged(CompanyModel companyModel) {
                CustomSpinnerSelectCompany customSpinnerSelectState = new CustomSpinnerSelectCompany(ApplyLeave.this, companyModel.getData());
                al.spnCompany.setAdapter(customSpinnerSelectState);
            }
        });

        al.spnCompany1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                LeaveListModel.Datum selectedItem = (LeaveListModel.Datum) adapterView.getSelectedItem();
                Leave_Type = Integer.parseInt(selectedItem.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        attendanceVm.getLeaveList().observe(ApplyLeave.this, new Observer<LeaveListModel>() {
            @Override
            public void onChanged(LeaveListModel leaveListModel) {
                CustomSpinnerSelectLeaveType customSpinnerSelectState = new CustomSpinnerSelectLeaveType(ApplyLeave.this, leaveListModel.getData());
                al.spnCompany1.setAdapter(customSpinnerSelectState);
            }
        });


        al.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        al.selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        al.txtApplyLeave.setOnClickListener(this);

    }

    private void selectImage() {
        final CharSequence[] options = {/*"Take Photo", */"Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ApplyLeave.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

               /* if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "sankalp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    Uri photoURI = FileProvider.getUriForFile(Measurement.this, Measurement.this.getApplicationContext().getPackageName() + ".provider", createImageFile());
                    startActivityForResult(intent, 1);
                } else*/

                if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

            private File createImageFile() {
                return createImageFile();
            }
        });
        builder.show();
    }


    @SuppressLint("LongLogTag")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File sankalp : f.listFiles()) {
                    if (sankalp.getName().equals("sankalp.jpg")) {
                        f = sankalp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    bitmap = getResizedBitmap(bitmap, 400);
                    al.IdProf.setImageBitmap(bitmap);
                    BitMapToString(bitmap);
                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail = getResizedBitmap(thumbnail, 400);
                Log.w("path of image from gallery......******************.........", picturePath + "");
                al.IdProf.setImageBitmap(thumbnail);
                BitMapToString(thumbnail);
            }
        }
    }


    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    private void SendDetail() {
        final ProgressDialog loading = new ProgressDialog(ApplyLeave.this);
        loading.setMessage("Please Wait...");
        loading.show();
        loading.setCanceledOnTouchOutside(false);
        RetryPolicy mRetryPolicy = new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApplyLeave.ConfiURL.Registration_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            loading.dismiss();
                            Log.d("JSON", response);

                            JSONObject eventObject = new JSONObject(response);
                            String error_status = eventObject.getString("error");
                            if (error_status.equals("true")) {
                                String error_msg = eventObject.getString("msg");
                                ContextThemeWrapper ctw = new ContextThemeWrapper(ApplyLeave.this, R.style.Theme_AlertDialog);
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                                alertDialogBuilder.setTitle("Vendor Detail");
                                alertDialogBuilder.setCancelable(false);
                                alertDialogBuilder.setMessage(error_msg);
                                alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                                alertDialogBuilder.show();

                            } else {
                                String error_msg = eventObject.getString("msg");
                                ContextThemeWrapper ctw = new ContextThemeWrapper(ApplyLeave.this, R.style.Theme_AlertDialog);
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                                alertDialogBuilder.setTitle("Registration");
                                alertDialogBuilder.setCancelable(false);
                                alertDialogBuilder.setMessage(error_msg);
                                //                                alertDialogBuilder.setIcon(R.drawable.doubletick);
                                alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(ApplyLeave.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                alertDialogBuilder.show();
                            }
                        } catch (Exception e) {
                            Log.d("Tag", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            ContextThemeWrapper ctw = new ContextThemeWrapper(ApplyLeave.this, R.style.Theme_AlertDialog);
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                            alertDialogBuilder.setTitle("No connection");
                            alertDialogBuilder.setMessage(" Connection time out error please try again ");
                            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                            alertDialogBuilder.show();
                        } else if (error instanceof AuthFailureError) {
                            ContextThemeWrapper ctw = new ContextThemeWrapper(ApplyLeave.this, R.style.Theme_AlertDialog);
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                            alertDialogBuilder.setTitle("Connection Error");
                            alertDialogBuilder.setMessage(" Authentication failure connection error please try again ");
                            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                            alertDialogBuilder.show();
                            //TODO
                        } else if (error instanceof ServerError) {
                            ContextThemeWrapper ctw = new ContextThemeWrapper(ApplyLeave.this, R.style.Theme_AlertDialog);
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                            alertDialogBuilder.setTitle("Connection error");
                            alertDialogBuilder.setMessage("Connection error please try again");
                            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                            alertDialogBuilder.show();
                            //TODO
                        } else if (error instanceof NetworkError) {
                            ContextThemeWrapper ctw = new ContextThemeWrapper(ApplyLeave.this, R.style.Theme_AlertDialog);
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                            alertDialogBuilder.setTitle("Connection error");
                            alertDialogBuilder.setMessage("Network connection error please try again");
                            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                            alertDialogBuilder.show();
                            //TODO
                        } else if (error instanceof ParseError) {
                            ContextThemeWrapper ctw = new ContextThemeWrapper(ApplyLeave.this, R.style.Theme_AlertDialog);
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                            alertDialogBuilder.setTitle("Error");
                            alertDialogBuilder.setMessage("Parse error");
                            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                            alertDialogBuilder.show();
                        }
                        //                        Toast.makeText(Login_Activity.this,error.toString(), Toast.LENGTH_LONG ).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_User_Document1, Document_img1);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(mRetryPolicy);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("user_id", preferences.getStringPreference(USER_ID));
        stringStringMap.put("year", preferences.getStringPreference(YEAR_ID));
        stringStringMap.put("company_id", "" + CompanyId);
        stringStringMap.put("leave_type", "" + Leave_Type);
        stringStringMap.put("start_date", al.etFromDate.getText().toString());
        stringStringMap.put("end_date", al.etToDate.getText().toString());
        stringStringMap.put("reason", al.etReason.getText().toString());
        stringStringMap.put("image", Document_img1);
        progressDialog(ApplyLeave.this);
        counterVm.applyLeave(stringStringMap).observe(ApplyLeave.this, new Observer<LeaveModel>() {
            @Override
            public void onChanged(LeaveModel leaveModel) {
                if (leaveModel.getResult().equals("1")) {
                    progressdialog.dismiss();
                    myToast(ApplyLeave.this, "Submitted successfully!");
                    finish();
                } else {
                    progressdialog.dismiss();
                    myToast(ApplyLeave.this, "Fail");
                }
            }
        });


    }

    private void getData() {

    }


    private static class ConfiURL {
        public static String Registration_URL;
    }

}