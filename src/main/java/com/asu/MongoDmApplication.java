package com.asu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MongoDmApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoDmApplication.class, args);
		
		
		/*try {

			MongoCredential credential = MongoCredential.createCredential("admin", "admin", "sdrc@mongo6356".toCharArray());
			MongoClient mongoClient = new MongoClient(new ServerAddress("192.168.1.10", 27017), Arrays.asList(credential));
			
			DB db = mongoClient.getDB("TedEngineAddDev");
			DBCollection collection = db.getCollection("imageCollections");

			String newFileName = "Jhumka";

			File imageFile = new File("E:\\unnamed.jpg");

			// create a "photo" namespace
			GridFS gfsPhoto = new GridFS(db, "photo");

			// get image file from local drive
			GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);

			// set a new filename for identify purpose
			gfsFile.setFilename(newFileName);

			// save the image file into mongoDB
			gfsFile.save();

			// print the result
			DBCursor cursor = gfsPhoto.getFileList();
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}

			// get image file by it's filename
			GridFSDBFile imageForOutput = gfsPhoto.findOne(newFileName);
			
			// save it into a new image file
			imageForOutput.writeTo("c:\\JavaWebHostingNew.png");

			// remove the image file from mongoDB
			gfsPhoto.remove(gfsPhoto.findOne(newFileName));

			System.out.println("Done");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/

	

	}

	
}
