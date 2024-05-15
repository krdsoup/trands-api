package kr.co.trands.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Component
public class AwsUtil {
	
	@Autowired
    private AmazonS3 awsS3Client;
	
	public List<S3ObjectSummary> getS3ObjectSummaryList(String bucket, String filePath, String fileName) {
        List<S3ObjectSummary> summaryList = new ArrayList<>();

        ListObjectsRequest request = new ListObjectsRequest();

        String prefix = filePath.replace(fileName, "");

        request.setBucketName(bucket);
        request.setPrefix(prefix);

        ObjectListing obj = awsS3Client.listObjects(request);

        do{
            obj = awsS3Client.listObjects(request);
            summaryList.addAll(obj.getObjectSummaries());
            request.setMarker(obj.getNextMarker());
        } while (obj.isTruncated());

        return summaryList;
    }
    
}
