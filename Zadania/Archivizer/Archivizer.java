import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Archivizer implements ArchivizerInterface{

	private void zipTarget(File compressionTarget,  String compressionTargetName, ZipOutputStream zOut) throws IOException {
		//kompresja katalogu
		if (compressionTarget.isDirectory()) { 
			if (compressionTargetName.endsWith("/"))
				zOut.putNextEntry(new ZipEntry(compressionTargetName+"/"));
			else
				zOut.putNextEntry(new ZipEntry(compressionTargetName+"/"));
			zOut.closeEntry();
			
			//kompresja dla kazdego podkatalogu i podpliku
			File[] insideFiles = compressionTarget.listFiles();
			for (File f : insideFiles) 
				zipTarget(f, compressionTargetName + "/" + f.getName(), zOut);
		}
		//kompresja pliku
		else {
			
			FileInputStream fIn = new FileInputStream(compressionTarget);
			zOut.putNextEntry(new ZipEntry(compressionTargetName));
			
			byte[] bufor = new byte[128];
			int len = 0;
			
			while((len = fIn.read(bufor)) >= 0)
				zOut.write(bufor, 0, len);			
			fIn.close();
		}
	}
	
	@Override
	public int compress(String dir, String filename) {		
		try {
			FileOutputStream fOut = new FileOutputStream(filename);
			ZipOutputStream zOut = new ZipOutputStream(fOut);
			File compressionTarget = new File(dir);			
			
			//rekurencyjna kompresja (katalog moze zawierac inne pliki i katalogi)
			File[] insideFiles = compressionTarget.listFiles();
			for (File f : insideFiles) 	
				zipTarget(f, f.getName(), zOut);
			
			//zipTarget(f, compressionTargetName + "/" + f.getName(), zOut, false);
			zOut.close();
			fOut.close();
			
			File resultFile = new File(filename);
			if (compressionTarget.exists())
				return (int) resultFile.length();
			return -1;
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}
	

	@Override
	public void decompress(String filename, String dir) {
		try {
			String compressedFile = filename;
	        	File destinationCatalog = new File(dir);
	        
			byte[] bufor = new byte[128];
			FileInputStream fIn = new FileInputStream(compressedFile);
	        ZipInputStream zIn = new ZipInputStream(fIn);
	        
	        ZipEntry zipEntry = zIn.getNextEntry();
	        
	        while (zipEntry != null) {
	            File newFile = new File(destinationCatalog, zipEntry.getName());
	            if (zipEntry.isDirectory()) 
	            	newFile.mkdirs();
	            else {
	                FileOutputStream fOut = new FileOutputStream(newFile);	                
	                int len;
	                while ((len = zIn.read(bufor)) > 0) 
	                	fOut.write(bufor, 0, len);	                
	                fOut.close();
	            }
	            zipEntry = zIn.getNextEntry();
	        }
	        zIn.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		Archivizer arch = new Archivizer();
		
		String catalog = "/home/szy/Pulpit/czynacha/t1";
		String resultFile = "/home/szy/Pulpit/czynacha/t1.result";
	
		System.out.println(arch.compress(catalog, resultFile));
		
		/////////////////////////////////////
		String fileZip = "/home/szy/Pulpit/czynacha/t1.result";
        	String destination = "/home/szy/Pulpit/czynacha/DoMnieDaj";
		

        	arch.decompress(fileZip, destination);
	
	
	
	}

}
