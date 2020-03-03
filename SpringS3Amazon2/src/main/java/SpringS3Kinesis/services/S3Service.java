package SpringS3Kinesis.services;

public interface S3Service {
	public void downloadFileFromBucket(String bucketName, String keyName);
}
