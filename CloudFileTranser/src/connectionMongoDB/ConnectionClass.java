package connectionMongoDB;
import java.io.File;
import java.io.IOException;
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
public class ConnectionClass {
	
		public DB connection(String database) throws MongoException, MongoException{
			
			Mongo mongo = null;
			try {
				mongo = new Mongo("127.0.0.1", 27017);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DB db = mongo.getDB(database);
			return db;
		}
		
//		public 
	
		public String retrieveFile(String fileName) throws MongoException, IOException {
			Mongo mongo = new Mongo("127.0.0.1", 27017);
			DB db = mongo.getDB("CloudApplication");
			GridFS gridfs = new GridFS(db, "FileMaster");
			DBCursor cursor = gridfs.getFileList();
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}

			GridFSDBFile imageForOutput = gridfs.findOne(fileName);

			imageForOutput.writeTo("downloaded_"+fileName);
			return "downloaded_"+fileName;
			
		}

		public void saveFile(String fileName) throws IOException, MongoException {
			Mongo mongo = new Mongo("127.0.0.1", 27017);
			DB db = mongo.getDB("CloudApplication");

			File file = new File(fileName);

			GridFS gridfs = new GridFS(db, "FileMaster");
			GridFSInputFile gfsFile = gridfs.createFile(file);
			gfsFile.setFilename(fileName);
			gfsFile.save();
	 
			BasicDBObject info = new BasicDBObject();
	                info.put("name", "MongoDB");
	                info.put("fileName", fileName);
	                info.put("rawName", fileName);
	                info.put("rawPath", "fileClient//");
			
		}
	}