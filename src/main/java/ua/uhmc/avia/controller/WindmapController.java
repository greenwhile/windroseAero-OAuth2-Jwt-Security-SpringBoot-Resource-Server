package ua.uhmc.avia.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.uhmc.avia.component.WindmapComponent;
import ua.uhmc.avia.handler.WindmapComponentHandler;

import java.io.*;

//@RestController
//@RequestMapping("/api")
@Controller
public class WindmapController {

//    @RequestMapping(value = "/search", method = RequestMethod.POST, headers = "Accept=application/x-www-form-urlencoded")
//    @ResponseBody
////    @PreAuthorize("#oauth2.hasScope('windmap') and #oauth2.hasScope('read')")
//    public WindmapComponent post_search(
//                                        HttpServletRequest request,
////                                        @RequestParam(required=false,name="action") String action,
//                                        @RequestParam(required=true,name="areas") String req_area,
//                                        @RequestParam(required=true,name="grib2Dates") String req_date,
//                                        @RequestParam(required=true,name="levels") String req_level,
//                                        @RequestParam(required=true,name="validities") String req_validity,
//                                        Model model) throws IOException {
//
////        System.out.println("resource url: " + request.getRequestURL());
////        System.out.println("req_area: " + request.getParameter("areas"));
////        System.out.println("req_date: " + request.getParameter("grib2Dates"));
////        System.out.println("req_level: " + request.getParameter("levels"));
////        System.out.println("req_validity: " + request.getParameter("validities"));
//
//        System.out.println("resource url: " + request.getRequestURL());
////        System.out.println("action: " + action);
//        System.out.println("req_area: " + req_area);
//        System.out.println("req_date: " + req_date);
//        System.out.println("req_level: " + req_level);
//        System.out.println("req_validity: " + req_validity);
//        System.out.println("queryString: " + request.getQueryString());
//
//        Windmap windmap = new Windmap(
//                request.getParameter("areas"),
//                request.getParameter("grib2Dates"),
//                request.getParameter("levels"),
//                request.getParameter("validities"));
//
//        System.out.println("windmap from resource controller: " + windmap);
//
//        WindmapHandler handler = new WindmapHandler(windmap);
//
//        System.out.println("\nValidityDependentList: " + handler.getValidityDependentList().size());
//        for(String item: handler.getValidityDependentList()){
//            System.out.println(item);
//        }
//
//        System.out.println("\nLevelDependentList: " + handler.getLevelDependentList().size());
//        for(String item: handler.getLevelDependentList()){
//            System.out.println(item);
//        }
//
//        String imageFilename = handler.getCurrentImageName().replace(".png", "");
////        System.out.println("output imageFilename: " + imageFilename + " lev: " + handler.getWindmapComponent().getLevelIndex() + " val: " + handler.getWindmapComponent().getValidityIndex());
//
//        WindmapComponent windmapComponent = handler.getWindmapComponent();
////        System.out.println("windmapComponent: " + windmapComponent);
//
//        byte[] currentWindmapImage = handler.getCurrentWindmapImage();
//        System.out.println("currentWindmapImage: " + currentWindmapImage.length);
//
//        return windmapComponent; // currentWindmapImage; // imageFilename; // ResponseEntity.ok(windmapComponent);
//    }

    @RequestMapping(value = "/windmap/{windmapImageFilename}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    @PreAuthorize("#oauth2.hasScope('windmap') and #oauth2.hasScope('read')")
    public WindmapComponent current(@PathVariable String windmapImageFilename) throws IOException {

        windmapImageFilename = windmapImageFilename.replace(" ", "+").concat(".png");
        System.out.println("input imageFilename: " + windmapImageFilename);

        WindmapComponentHandler handler = new WindmapComponentHandler(windmapImageFilename);
//        String imageFilename = handler.getUpImage().replace(".png", "");
        System.out.println("output imageFilename: " + handler.getWindmapComponent().getWindmapImageFilename() + " lev: " + handler.getWindmapComponent().getLevelIndex() + " val: " + handler.getWindmapComponent().getValidityIndex());

        WindmapComponent windmapComponent = handler.getWindmapComponent();
        System.out.println("windmapComponent: " + windmapComponent.getWindmapImageFilename());

//        byte[] currentWindmapImage = handler.getWindmapComponent().getCurrentWindmapImage();
//        System.out.println("currentWindmapImage: " + currentWindmapImage.length);

        return windmapComponent;
    }

    @RequestMapping(value = "/windmap/up/{windmapImageFilename}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    @PreAuthorize("#oauth2.hasScope('windmap') and #oauth2.hasScope('read')")
    public WindmapComponent up(@PathVariable String windmapImageFilename) throws IOException {

        System.out.println("FROM /up Resources Controller");

        windmapImageFilename = windmapImageFilename.replace(" ", "+").concat(".png");
        System.out.println("input imageFilename: " + windmapImageFilename);

        WindmapComponentHandler handler = new WindmapComponentHandler(windmapImageFilename);
        String imageFilename = handler.getUpImage().replace(".png", "");
        System.out.println("output imageFilename: " + imageFilename + " lev: " + handler.getWindmapComponent().getLevelIndex() + " val: " + handler.getWindmapComponent().getValidityIndex());

        WindmapComponent windmapComponent = handler.getWindmapComponent();
        System.out.println("windmapComponent: " + windmapComponent.getWindmapImageFilename());

//        byte[] currentWindmapImage = handler.getWindmapComponent().getCurrentWindmapImage();
//        System.out.println("currentWindmapImage: " + currentWindmapImage.length);

        return windmapComponent;
    }

    @RequestMapping(value = "/windmap/down/{windmapImageFilename}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    @PreAuthorize("#oauth2.hasScope('windmap') and #oauth2.hasScope('read')")
    public WindmapComponent down(@PathVariable String windmapImageFilename) throws IOException {

        System.out.println("FROM /down Resources Controller");

        windmapImageFilename = windmapImageFilename.replace(" ", "+").concat(".png");
        System.out.println("input imageFilename: " + windmapImageFilename);

        WindmapComponentHandler handler = new WindmapComponentHandler(windmapImageFilename);
        String imageFilename = handler.getDownImage().replace(".png", "");
        System.out.println("output imageFilename: " + imageFilename + " lev: " + handler.getWindmapComponent().getLevelIndex() + " val: " + handler.getWindmapComponent().getValidityIndex());

        WindmapComponent windmapComponent = handler.getWindmapComponent();
        System.out.println("windmapComponent: " + windmapComponent.getWindmapImageFilename());

//        byte[] currentWindmapImage = handler.getWindmapComponent().getCurrentWindmapImage();
//        System.out.println("currentWindmapImage: " + currentWindmapImage.length);

        return windmapComponent;
    }

    @RequestMapping(value = "/windmap/right/{windmapImageFilename}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    @PreAuthorize("#oauth2.hasScope('windmap') and #oauth2.hasScope('read')")
    public WindmapComponent right(@PathVariable String windmapImageFilename) throws IOException {

        System.out.println("FROM /right Resources Controller");

        windmapImageFilename = windmapImageFilename.replace(" ", "+").concat(".png");
        System.out.println("input imageFilename: " + windmapImageFilename);

        WindmapComponentHandler handler = new WindmapComponentHandler(windmapImageFilename);
        String imageFilename = handler.getRightImage().replace(".png", "");
        System.out.println("output imageFilename: " + imageFilename + " lev: " + handler.getWindmapComponent().getLevelIndex() + " val: " + handler.getWindmapComponent().getValidityIndex());

        WindmapComponent windmapComponent = handler.getWindmapComponent();
        System.out.println("windmapComponent: " + windmapComponent.getWindmapImageFilename());

//        byte[] currentWindmapImage = handler.getWindmapComponent().getCurrentWindmapImage();
//        System.out.println("currentWindmapImage: " + currentWindmapImage.length);

        return windmapComponent;
    }

    @RequestMapping(value = "/windmap/left/{windmapImageFilename}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    @PreAuthorize("#oauth2.hasScope('windmap') and #oauth2.hasScope('read')")
    public WindmapComponent left(@PathVariable String windmapImageFilename) throws IOException {

        System.out.println("FROM /left Resources Controller");

        windmapImageFilename = windmapImageFilename.replace(" ", "+").concat(".png");
        System.out.println("input imageFilename: " + windmapImageFilename);

        WindmapComponentHandler handler = new WindmapComponentHandler(windmapImageFilename);
        String imageFilename = handler.getLeftImage().replace(".png", "");
        System.out.println("output imageFilename: " + imageFilename + " lev: " + handler.getWindmapComponent().getLevelIndex() + " val: " + handler.getWindmapComponent().getValidityIndex());

        WindmapComponent windmapComponent = handler.getWindmapComponent();
        System.out.println("windmapComponent: " + windmapComponent.getWindmapImageFilename());

//        byte[] currentWindmapImage = handler.getWindmapComponent().getCurrentWindmapImage();
//        System.out.println("currentWindmapImage: " + currentWindmapImage.length);

        return windmapComponent;
    }

    @RequestMapping(value = "/windmap/save/{windmapImageFilename}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    @PreAuthorize("#oauth2.hasScope('windmap') and #oauth2.hasScope('read')")
    public WindmapComponent save(@PathVariable String windmapImageFilename) throws IOException {

        System.out.println("FROM /save Resources Controller");

        windmapImageFilename = windmapImageFilename.replace(" ", "+").concat(".png");
        System.out.println("input imageFilename: " + windmapImageFilename);

        WindmapComponentHandler handler = new WindmapComponentHandler(windmapImageFilename);
        boolean success = handler.writeToFile();
        System.out.println("file " + handler.getWindmapComponent().getWindmapImageFilename() + " is successfully saved");

        WindmapComponent windmapComponent = handler.getWindmapComponent();
        System.out.println("windmapComponent: " + windmapComponent.getWindmapImageFilename());

//        byte[] currentWindmapImage = handler.getWindmapComponent().getCurrentWindmapImage();
//        System.out.println("currentWindmapImage: " + currentWindmapImage.length);

        return windmapComponent;
    }

//    @RequestMapping(value = "/windmap", method = RequestMethod.GET)
//    @ResponseBody
//    @PreAuthorize("#oauth2.hasScope('windmap') and #oauth2.hasScope('read')")
//    public /*byte[]*/ Windmap getImageByParams() throws IOException {
//        FileHandler handler = new FileHandler();
//        String filename = "u_QWAC10_NorthAtl_H 06_K=1406.png";
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        for(Resource resource : ResourceHandler.resources){
//            String resource_name = resource.getFile().getName();
//            String request_param_name = filename.replace(" ", "+");
////            System.out.println(resource_name.equals(request_param_name) + " *** " + "resources: " + resource_name + "  " + request_param_name);
//            if(resource_name.equals(request_param_name)){
//                handler.setFilename(resource.getFile().getName());
//                byteArrayOutputStream = getImageBytes(resource.getFile());
//            }
//        }
//        return new Windmap(handler.getTerritory(), handler.getObservation(), handler.getLevel(),
//                handler.getValidity()); // byteArrayOutputStream.toByteArray();
//    }

//    private ByteArrayOutputStream getImageBytes(File file) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        try{
//            InputStream inputStream = new FileInputStream(file);
//            byte[] buffer = new byte[8192];
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                byteArrayOutputStream.write(buffer, 0, bytesRead);
//            }
//        } catch(IOException ex){
//            ex.printStackTrace();
//        }
//        return byteArrayOutputStream;
//    }

//    @RequestMapping(value = "/windmap/{imageFileName}", method = RequestMethod.GET)
//    @ResponseBody
//    public byte[] getWindmapObject(@PathVariable("imageFileName") String imageFileName){
//        System.out.println("request filename: " + imageFileName);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        try {
//            imageFileName = imageFileName.concat(".png").replace(" ", "+");
//            System.out.println("repared request filename: " + imageFileName);
//            for(Resource resource : ResourceHandler.resources){
//                System.out.println("ress filename: " + resource.getFilename() + " ? " + imageFileName + " => " + resource.getFile().getName().equals(imageFileName));
//                if(resource.getFile().getName().equals(imageFileName)){
//                    System.out.println("equals filenames: " + resource.getFilename());
////                    String request_param_name = resource.getFilename().replace(" ", "+");
//                    if(resource.getFilename().equals(imageFileName)){
//                        byteArrayOutputStream = getImageBytes(resource.getFile());
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return byteArrayOutputStream.toByteArray();
//    }
}
