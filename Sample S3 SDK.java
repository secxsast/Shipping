
package com.amazonaws.samples;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;


public class S3Sdk {

    @Value("${app.awsServices.buckName1}")
    private String bucket1;

    @Value("${app.awsServices.buckName2}")
    private String bucket2;

	public void Foo() {

		AmazonS3.putObject(bucket1);
		AmazonS3.putObject(bucket2);

	}
	public String getParam(javax.servlet.http.HttpServletRequest req, String paramName) 
	{
	    String param = req.getParameter(paramName);
	    if ( param == null || param.equals("") ) 
	          return "";
	    return param;
        }
	public void DoSomething(javax.servlet.http.HttpServletRequest request)
	{
		String paramData = getParam(request,"item");
		String key = "MyObjectKey";
		AmazonS3.putObject(new PutObjectRequest( bucket1, key, paramData));
	}

	public void DoSomething2(javax.servlet.http.HttpServletRequest request)
	{
		String pass = getParam(request,"item");
		String key = "MyObjectKey";
		AmazonS3.putObject(new PutObjectRequest( bucket1, key, pass));
	}
}
}