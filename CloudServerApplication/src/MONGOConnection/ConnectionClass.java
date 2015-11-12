package MONGOConnection;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
public class ConnectionClass{
	 
		

		public static void main(String[] args) throws IOException {
			try {
			//
			// Connect to MongoDB (without authentification for the time being)
			// And get a handle on the collection used to store the metadata
			//
				Mongo mongo = new Mongo("ec2-54-183-247-63.us-west-1.compute.amazonaws.com", 27017);

			DB db = mongo.getDB("CloudApplication");
			DBCollection collection = db.getCollection("FileMaster");
	 
			//
			// The biiiiig file to be stored to MongoDB
			//
			File file = new File("C:/Users/dharmisha/OneDrive/Courses/180-90/H&H.pdf");
			
	 
			//
			// Store the file to MongoDB using GRIDFS
			//
			GridFS gridfs = new GridFS(db, "FileMaster");
			GridFSInputFile gfsFile = gridfs.createFile(file);
			gfsFile.setFilename("H&H.pdf");
			gfsFile.save();
	 
			//
			// Let's create a new JSON document with some "metadata" information on the download
			//
			BasicDBObject info = new BasicDBObject();
	                info.put("name", "MongoDB");
	                info.put("fileName", "H&H.pdf");
	                info.put("rawName", "H&H.pdf");
	                info.put("rawPath", "C:/Users/dharmisha/OneDrive/Courses/180-90/");
	 
	                //
	                // Let's store our document to MongoDB
	                //
			collection.insert(info, WriteConcern.SAFE);
			// print the result
						DBCursor cursor = gridfs.getFileList();
						while (cursor.hasNext()) {
							System.out.println(cursor.next());
						}
			// get image file by it's filename
			GridFSDBFile imageForOutput = gridfs.findOne("H&H.pdf");
			// save it into a new image file
			imageForOutput.writeTo("D:\\H&H.pdf");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (MongoException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}