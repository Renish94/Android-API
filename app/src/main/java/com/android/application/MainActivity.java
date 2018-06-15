package com.android.application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.api.API;
import com.android.api.common.Priority;
import com.android.api.error.APIError;
import com.android.api.interfaces.AnalyticsListener;
import com.android.api.interfaces.ParsedRequestListener;
import com.android.api.interfaces.StringRequestListener;
import com.android.application.model.Post;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView txt_output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_output = findViewById(R.id.txt_output);

        findViewById(R.id.btn_get_api).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                API.get("https://jsonplaceholder.typicode.com/posts/{id}")
                        .addPathParameter("id", "1")
                        .setTag(this)
                        .setPriority(Priority.LOW)
                        .doNotCacheResponse()
                        .build()
                        .setAnalyticsListener(new AnalyticsListener() {
                            @Override
                            public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                                Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                                Log.d(TAG, " bytesSent : " + bytesSent);
                                Log.d(TAG, " bytesReceived : " + bytesReceived);
                                Log.d(TAG, " isFromCache : " + isFromCache);
                            }
                        })
                        .getAsString(new StringRequestListener() {
                            @Override
                            public void onResponse(String response) {
                                Log.d(TAG, "onResponse: " + response);
                                txt_output.setText(response);
                            }

                            @Override
                            public void onError(APIError apiError) {
                                if (apiError.getErrorCode() != 0) {
                                    // received APIError from server

                                    // apiError.getErrorCode() - the APIError code from server
                                    Log.d(TAG, "onError errorCode : " + apiError.getErrorCode());

                                    // apiError.getErrorBody() - the APIError body from server
                                    Log.d(TAG, "onError errorBody : " + apiError.getErrorBody());

                                    // apiError.getErrorDetail() - just a APIError detail
                                    Log.d(TAG, "onError errorDetail : " + apiError.getErrorDetail());
                                } else {
                                    // apiError.getErrorDetail() : connectionError, parseError, requestCancelledError
                                    Log.d(TAG, "onError errorDetail : " + apiError.getErrorDetail());
                                }
                            }
                        });
            }
        });

        findViewById(R.id.btn_post_api).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API.post("https://jsonplaceholder.typicode.com/posts")
                        .addBodyParameter("id", "101")
                        .addBodyParameter("userId", "1")
                        .addBodyParameter("title", "this is test title")
                        .addBodyParameter("body", "this is test body")
                        .setTag(this)
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsString(new StringRequestListener() {
                            @Override
                            public void onResponse(String response) {
                                Log.d(TAG, "onResponse: " + response);
                                txt_output.setText(response);
                            }

                            @Override
                            public void onError(APIError apiError) {
                                if (apiError.getErrorCode() != 0) {
                                    // received APIError from server

                                    // apiError.getErrorCode() - the APIError code from server
                                    Log.d(TAG, "onError errorCode : " + apiError.getErrorCode());

                                    // apiError.getErrorBody() - the APIError body from server
                                    Log.d(TAG, "onError errorBody : " + apiError.getErrorBody());

                                    // apiError.getErrorDetail() - just a APIError detail
                                    Log.d(TAG, "onError errorDetail : " + apiError.getErrorDetail());
                                } else {
                                    // apiError.getErrorDetail() : connectionError, parseError, requestCancelledError
                                    Log.d(TAG, "onError errorDetail : " + apiError.getErrorDetail());
                                }
                            }
                        });
            }
        });

        findViewById(R.id.btn_put_api).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API.put("https://jsonplaceholder.typicode.com/posts/{id}")
                        .addPathParameter("id", "1")
                        .addBodyParameter("userId", "1")
                        .addBodyParameter("title", "this is updated test title")
                        .addBodyParameter("body", "this is updated test body")
                        .setTag(this)
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsString(new StringRequestListener() {
                            @Override
                            public void onResponse(String response) {
                                Log.d(TAG, "onResponse: " + response);
                                txt_output.setText(response);
                            }

                            @Override
                            public void onError(APIError apiError) {
                                if (apiError.getErrorCode() != 0) {
                                    // received APIError from server

                                    // apiError.getErrorCode() - the APIError code from server
                                    Log.d(TAG, "onError errorCode : " + apiError.getErrorCode());

                                    // apiError.getErrorBody() - the APIError body from server
                                    Log.d(TAG, "onError errorBody : " + apiError.getErrorBody());

                                    // apiError.getErrorDetail() - just a APIError detail
                                    Log.d(TAG, "onError errorDetail : " + apiError.getErrorDetail());
                                } else {
                                    // apiError.getErrorDetail() : connectionError, parseError, requestCancelledError
                                    Log.d(TAG, "onError errorDetail : " + apiError.getErrorDetail());
                                }
                            }
                        });
            }
        });

        findViewById(R.id.btn_delete_api).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API.delete("https://jsonplaceholder.typicode.com/posts/{id}")
                        .addPathParameter("id", "1")
                        .setTag(this)
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsString(new StringRequestListener() {
                            @Override
                            public void onResponse(String response) {
                                Log.d(TAG, "onResponse: " + response);
                                txt_output.setText(response);
                            }

                            @Override
                            public void onError(APIError apiError) {
                                if (apiError.getErrorCode() != 0) {
                                    // received APIError from server

                                    // apiError.getErrorCode() - the APIError code from server
                                    Log.d(TAG, "onError errorCode : " + apiError.getErrorCode());

                                    // apiError.getErrorBody() - the APIError body from server
                                    Log.d(TAG, "onError errorBody : " + apiError.getErrorBody());

                                    // apiError.getErrorDetail() - just a APIError detail
                                    Log.d(TAG, "onError errorDetail : " + apiError.getErrorDetail());
                                } else {
                                    // apiError.getErrorDetail() : connectionError, parseError, requestCancelledError
                                    Log.d(TAG, "onError errorDetail : " + apiError.getErrorDetail());
                                }
                            }
                        });
            }
        });

        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_output.setText("");
            }
        });

        API.get("https://jsonplaceholder.typicode.com/posts")
                .setTag(this)
                .setPriority(Priority.LOW)
                .doNotCacheResponse()
                .build()
                .getAsObjectList(Post.class, new ParsedRequestListener<List<Post>>() {
                    @Override
                    public void onResponse(List<Post> response) {
                        Log.d(TAG, "onResponse: " + response.size());
                    }

                    @Override
                    public void onError(APIError apiError) {
                        if (apiError.getErrorCode() != 0) {
                            // received APIError from server

                            // apiError.getErrorCode() - the APIError code from server
                            Log.d(TAG, "onError errorCode : " + apiError.getErrorCode());

                            // apiError.getErrorBody() - the APIError body from server
                            Log.d(TAG, "onError errorBody : " + apiError.getErrorBody());

                            // apiError.getErrorDetail() - just a APIError detail
                            Log.d(TAG, "onError errorDetail : " + apiError.getErrorDetail());
                        } else {
                            // apiError.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d(TAG, "onError errorDetail : " + apiError.getErrorDetail());
                        }
                    }
                });
    }
}
