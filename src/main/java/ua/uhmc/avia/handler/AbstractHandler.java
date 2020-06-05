package ua.uhmc.avia.handler;

import org.springframework.core.io.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHandler {

    protected ByteArrayOutputStream getImageBytes(File file) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            InputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
        } catch(IOException ex){
            ex.printStackTrace();
        }
        return byteArrayOutputStream;
    }

    public List<Resource> getImagesList(){
        List<Resource> imagesList = new ArrayList<>();
        for(Resource resource : ResourceHandler.resources){
            imagesList.add(resource);
        }
        return imagesList;
    }

    protected boolean isWindmapPresent(String windmapImageFilename, List<String> windmapImagesList){
        for (int i = 0; i < windmapImagesList.size(); i++) {
            if(windmapImageFilename.equals(windmapImagesList.get(i))){
                return true;
            }
        }
        return false;
    }

    public abstract Integer getValidityDependentItem(String imageName, List<String>validityDependentList);
    public abstract Integer getLevelDependentItem(String imageName, List<String>levelDependentList);
}
