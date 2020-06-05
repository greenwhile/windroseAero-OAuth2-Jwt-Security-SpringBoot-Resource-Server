package ua.uhmc.avia.handler;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import ua.uhmc.avia.component.WindmapComponent;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

public class ResourceHandler implements AbstractHandlerI {

    private Resource resource;
    public static Resource[] resources;

    static {
        ClassLoader cl = ResourceHandler.class.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        try {
            resources = resolver.getResources("classpath:/windmaps/**");
            for(Resource resource : resources){
                System.out.println("res => " + resource.getFilename());
            }
//            clean();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResourceHandler() {
    }

    public ResourceHandler(Resource resource) {
        this.resource = resource;
    }

    private static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

//    private static void deleteResource(Resource resourcetoDelete){
//        for(int i = 0; i <resources.length; i++){
//            if(resources[i].equals(resourcetoDelete)){
//                resources[i] = resources[i+1];
//            }
//        }
//    }

    private static void clean() {
//        try{
            for(Resource resource : resources){
//                Date date = new Date(resource.getFile().lastModified());
//                Calendar calendar_last_modified = toCalendar(date);
//                Calendar calendar_current = toCalendar(new Date());
//                calendar_current.add(Calendar.DAY_OF_YEAR, -102); // change to -1

                System.out.println(resource.getFilename());

//                System.out.println("day: " + resource.getFilename() + "\n " + date + " " + getDateOfFileCreation(resource) + " " + resource.getFilename().substring(resource.getFile().getName().length()-8, resource.getFile().getName().length()-6) +
//                        calendar_last_modified.get(Calendar.YEAR) + " " + calendar_last_modified.get(Calendar.MONTH) + " " + calendar_last_modified.get(Calendar.DAY_OF_MONTH) + " " +
//                        calendar_current.get(Calendar.YEAR) + " " + calendar_current.get(Calendar.MONTH) + " " + calendar_current.get(Calendar.DAY_OF_MONTH) + " " +
//                        calendar_last_modified.compareTo(calendar_current));

//                if(calendar_last_modified.compareTo(calendar_current) < 0){
//                    deleteResource(resource);

//                    System.out.println("resources to del: " + resource.getFilename() + "\n " + date + " " +
//                            calendar_last_modified.get(Calendar.YEAR) + " " + calendar_last_modified.get(Calendar.MONTH) + " " + calendar_last_modified.get(Calendar.DAY_OF_MONTH) + " " +
//                            calendar_current.get(Calendar.YEAR) + " " + calendar_current.get(Calendar.MONTH) + " " + calendar_current.get(Calendar.DAY_OF_MONTH) + " " +
//                            calendar_last_modified.compareTo(calendar_current));
//                }
//                else{
//                    System.out.println(
//                            resource.getFilename() + " " + date + " " +
//                            calendar_last_modified.get(Calendar.YEAR) + " " + calendar_last_modified.get(Calendar.MONTH) + " " + calendar_last_modified.get(Calendar.DAY_OF_MONTH) + " " +
//                            calendar_current.get(Calendar.YEAR) + " " + calendar_current.get(Calendar.MONTH) + " " + calendar_current.get(Calendar.DAY_OF_MONTH) + " " +
//                            calendar_last_modified.compareTo(calendar_current));
//                }
//            }
//        }
//        catch(IOException ex){
//            ex.printStackTrace();
        }
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public String getWindmapImageFilename() {
        return "u_QW" + this.getT1() + this.getValidityCode() + this.getLevel() + "_" +
                this.getTerritory() + "_" + "H+" + this.getValidity() + "_" + "K=" +
                /*Calendar.getInstance().get(Calendar.DAY_OF_MONTH)*/ "09" + this.getObservation() + ".png";
    }

    @Override
    public String getTerritory() {
        String territoryFileName = null;
        try {
            territoryFileName = this.resource.getFile().getName().substring(9, this.resource.getFile().getName().length()-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return territoryFileName.substring(0, territoryFileName.indexOf("_"));
    }

    @Override
    public String getTerritoryCode() {
        try {
            return this.resource.getFile().getName().substring(4,5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getT1() {
        try {
            return this.resource.getFile().getName().substring(4, 5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getObservation() {
        try {
            return this.resource.getFile().getName().substring(this.resource.getFile().getName().length()-6, this.resource.getFile().getName().length()-4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getLevel() {
        try {
            return this.resource.getFile().getName().substring(6, 8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getValidity() {
        try {
            return this.resource.getFile().getName().substring(this.resource.getFile().getName().length()-13, this.resource.getFile().getName().length()-11);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getValidityCode() {
        try {
            return this.resource.getFile().getName().substring(5,6);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDateOfFileCreation(Resource resource){
        try {
            return resource.getFile().getName().substring(resource.getFile().getName().length()-8, resource.getFile().getName().length()-6);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "ResourceHandler{" +
                "resource=" + resource +
                '}';
    }
}
