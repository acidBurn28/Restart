package com.thegame.Restart;


/**
 * Created by Haus-PC on 14.03.2016.
 */

    import org.json.JSONException;
    import org.json.JSONObject;

    import android.app.Activity;
    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.loopj.android.http.AsyncHttpClient;
    import com.loopj.android.http.AsyncHttpResponseHandler;
    import com.loopj.android.http.RequestParams;
    /**
     *
     * Register Activity Class
     */
    public class Reg extends Activity {
        // Progress Dialog Object
        ProgressDialog prgDialog;
        // Error Msg TextView Object
        TextView errorMsg;
        // Name Edit View Object

        EditText phone;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.reg);
            // Find Error Msg Text View control by ID
            errorMsg = (TextView)findViewById(R.id.register_error);
            // Find Name Edit View control by ID
            phone = (EditText)findViewById(R.id.phoneget);


            // Instantiate Progress Dialog object
            prgDialog = new ProgressDialog(this);
            // Set Progress Dialog Text
            prgDialog.setMessage("Please wait...");
            // Set Cancelable as False
            prgDialog.setCancelable(false);
        }

        /**
         * Method gets triggered when Register button is clicked
         *
         * @param view
         */
        public void registerUser(View view){
            String email = phone.getText().toString();

            RequestParams params = new RequestParams();


                // When Email entered is Valid
                if(Utility.validate(email)){
                    // Put Http parameter username with value of Email Edit View control
                    params.put("phone", phone);
                    // Put Http parameter password with value of Password Edit View control

                    // Invoke RESTful Web Service with Http parameters
                    invokeWS(params);
                }
                // When Email is invalid
                else{
                    Toast.makeText(getApplicationContext(), "Please enter valid phone", Toast.LENGTH_LONG).show();
                }
            }



        /**
         * Method that performs RESTful webservice invocations
         *
         * @param params
         */
        public void invokeWS(RequestParams params){
            // Show Progress Dialog
            prgDialog.show();
            // Make RESTful webservice call using AsyncHttpClient object
            AsyncHttpClient client = new AsyncHttpClient();
            client.get("http://webcraft.gq/restart/v1/index.php/register",params ,new AsyncHttpResponseHandler() {
                // When the response returned by REST has Http response code '200'
                @Override
                public void onSuccess(String response) {
                    // Hide Progress Dialog
                    prgDialog.hide();
                    try {
                        // JSON Object
                        JSONObject obj = new JSONObject(response);
                        // When the JSON response has status boolean value assigned with true
                        if(obj.getBoolean("status")){
                            // Set Default Values for Edit View controls
                            setDefaultValues();
                            // Display successfully registered message using Toast
                            Toast.makeText(getApplicationContext(), "You are successfully registered!", Toast.LENGTH_LONG).show();
                        }
                        // Else display error message
                        else{
                            errorMsg.setText(obj.getString("error_msg"));
                            Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();

                    }
                }
                // When the response returned by REST has Http response code other than '200'
                @Override
                public void onFailure(int statusCode, Throwable error,
                                      String content) {
                    // Hide Progress Dialog
                    prgDialog.hide();
                    // When Http response code is '404'
                    if(statusCode == 404){
                        Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                    }
                    // When Http response code is '500'
                    else if(statusCode == 500){
                        Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                    }
                    // When Http response code other than 404, 500
                    else{
                        Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        /**
         * Method which navigates from Register Activity to Login Activity
         */
        public void navigatetoLoginActivity(View view){
            Intent loginIntent = new Intent(getApplicationContext(),Login.class);
            // Clears History of Activity
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
        }

        /**
         * Set degault values for Edit View controls
         */
        public void setDefaultValues(){

            phone.setText("");

        }

    }