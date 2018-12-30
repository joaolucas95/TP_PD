/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import logic.TripsManagement.TPlane.TPlane;
import logic.TripsManagement.TPlane.TPlaneFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import logic.Config;
import logic.NoPermissionException;
import logic.TAirlineDTO;
import logic.TPlaceFeedbackDTO;
import logic.TPlaceDTO;
import logic.TPlaneDTO;
import logic.TUserDTO;
import logic.TripsManagement.TAirline.TAirline;
import logic.TripsManagement.TAirline.TAirlineFacadeLocal;
import logic.TripsManagement.TPlace.TPlace;
import logic.TripsManagement.TPlace.TPlaceFacadeLocal;
import logic.TripsManagement.TPlaceFeedback.TPlacefeedback;
import logic.TripsManagement.TPlaceFeedback.TPlacefeedbackFacadeLocal;
import logic.UsersManagement.TUser;
import logic.UsersManagement.TUserFacadeLocal;
import logic.UsersManagement.UsersManagerLocal;

/**
 *
 * @author bruno
 */
@Singleton
public class TripsManager implements TripsManagerLocal {

    @EJB
    TPlaneFacadeLocal planeFacade;
    
    @EJB
    UsersManagerLocal userManager;

    @EJB
    TAirlineFacadeLocal airlineFacade;
    
    @EJB
    TPlacefeedbackFacadeLocal placeFeedbackFacade;
    
    @EJB
    TPlaceFacadeLocal placeFacade;
    
    
    //Planes
    @Override
    public List<TPlaneDTO> findAllPlanes(String username) throws NoPermissionException {
               
        verifyPermission(username, Config.OPERATOR);
        
        List<TPlaneDTO> tPlaneDTOList = new ArrayList<>();
        for(TPlane tplane : planeFacade.findAll())
        {
            tPlaneDTOList.add(planeToDTO(tplane));
        }
        return tPlaneDTOList;
    }

    @Override
    public TPlaneDTO findPlane(int id, String username) throws NoPermissionException {
        
        verifyPermission(username, Config.OPERATOR);

        TPlane plane = planeFacade.find(id);
        
        if(plane == null)
            return null;
        
        return planeToDTO(plane);
    }
    
    @Override
    public boolean addPlane(TPlaneDTO planeDTO, String username) throws NoPermissionException {
        
        verifyPermission(username, Config.OPERATOR);

        TPlane tplane = new TPlane();
        
        if(!validatePlaneDTO(planeDTO))
            return false;
        
        tplane.setPlanename(planeDTO.getPlaneName());
        tplane.setPlanelimit(planeDTO.getPlaneLimit());
        
        planeFacade.create(tplane);
        return true;
    }

    @Override
    public boolean editPlane(TPlaneDTO planeDTO, String username) throws NoPermissionException {
        
        verifyPermission(username, Config.OPERATOR);
        
        TPlane tplane = planeFacade.find(planeDTO.getId());
        
        if(tplane == null)
            return false;
        
        if(!validatePlaneDTO(planeDTO))
            return false;
        
        tplane.setPlanename(planeDTO.getPlaneName());
        tplane.setPlanelimit(planeDTO.getPlaneLimit());
        
        planeFacade.edit(tplane);
        return true;
    }

    @Override
    public boolean removePlane(TPlaneDTO planeDTO, String username) throws NoPermissionException {
        
        verifyPermission(username, Config.OPERATOR);
        
        TPlane tplane = planeFacade.find(planeDTO.getId());
        
        if(tplane == null)
            return false;
        planeFacade.remove(tplane);
        return true;
    }
    
    private TPlaneDTO planeToDTO(TPlane tplane){
        return new TPlaneDTO(tplane.getId(), tplane.getPlanename(), tplane.getPlanelimit());
    }
    
    private boolean validatePlaneDTO(TPlaneDTO planeDTO)
    {
        if(planeDTO.getPlaneName()== null || planeDTO.getPlaneName().isEmpty())
            return false;
        if(planeDTO.getPlaneLimit()<0)
            return false;
            
        return true;
    }
    
    //-------------------------------------------------------------------------------------------------------------------
    //Airline
    @Override
    public List<TAirlineDTO> findAllAirlines(String username) throws NoPermissionException {
        verifyPermission(username, Config.OPERATOR);
        
        List<TAirlineDTO> tAirlineDTOList = new ArrayList<>();
        for(TAirline tairline : airlineFacade.findAll())
        {
            tAirlineDTOList.add(airlineToDTO(tairline));
        }
        return tAirlineDTOList;
    }

    @Override
    public TAirlineDTO findAirline(int id, String username) throws NoPermissionException {
        verifyPermission(username, Config.OPERATOR);
        
        TAirline airline = airlineFacade.find(id);
        
        if(airline == null)
            return null;
        
        return airlineToDTO(airline);
    }

    @Override
    public boolean addAirline(TAirlineDTO airlineDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.OPERATOR);

        TAirline tairline = new TAirline();
        
        if(!validateAirlineDTO(airlineDTO))
            return false;
        
        tairline.setAirlinename(airlineDTO.getAirlineName());
        tairline.setPhonenumber(airlineDTO.getPhoneNumber());
        
        airlineFacade.create(tairline);
        return true;
    }

    @Override
    public boolean editAirline(TAirlineDTO airlineDTO, String username) throws NoPermissionException {
        
        verifyPermission(username, Config.OPERATOR);
        
        TAirline tairline = airlineFacade.find(airlineDTO.getId());
        
        if(airlineDTO == null)
            return false;
        
        if(!validateAirlineDTO(airlineDTO))
            return false;
        
        tairline.setAirlinename(airlineDTO.getAirlineName());
        tairline.setPhonenumber(airlineDTO.getPhoneNumber());
        
        airlineFacade.edit(tairline);
        return true;
    }

    @Override
    public boolean removeAirline(TAirlineDTO airlineDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.OPERATOR);
        
        TAirline tairline = airlineFacade.find(airlineDTO.getId());
        
        if(tairline == null)
            return false;
        airlineFacade.remove(tairline);
        return true;
    }
    
    private TAirlineDTO airlineToDTO(TAirline airline){
        return new TAirlineDTO(airline.getId(), airline.getAirlinename(), airline.getPhonenumber());
    }
    
    private boolean validateAirlineDTO(TAirlineDTO airlineDTO)
    {
        if(airlineDTO.getAirlineName()== null || airlineDTO.getAirlineName().isEmpty())
            return false;
        if(airlineDTO.getPhoneNumber()== null || airlineDTO.getPhoneNumber().isEmpty())
            return false;
        return true;
    }
    
    
    //-------------------------------------------------------------------------------------------------------------------
    //Place
    @Override
    public List<TPlaceDTO> findAllPlaces(String username) {
        List<TPlaceDTO> tplaceDTOList = new ArrayList<>();
        for(TPlace place : placeFacade.findAll())
        {
            tplaceDTOList.add(placeToDTO(place));
        }
        return tplaceDTOList;
    }

    @Override
    public TPlaceDTO findPlace(int id) {
        TPlace place = placeFacade.find(id);
        
        if(place == null)
            return null;
        
        return placeToDTO(place);
        
    }

    @Override
    public boolean addPlace(TPlaceDTO placeDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.OPERATOR);

        TPlace tplace = new TPlace();
          
        if(!validatePlaceDTO(placeDTO))
            return false;
        
        tplace.setCity(placeDTO.getCity());
        tplace.setCountry(placeDTO.getCountry());
        tplace.setTPlacefeedbackCollection(new ArrayList());
        if(placeDTO.getAddress() != null && !placeDTO.getAddress().isEmpty())
            tplace.setAddress(placeDTO.getAddress());
        
        
        placeFacade.create(tplace);
        return true;
    }

    @Override
    public boolean editPlace(TPlaceDTO placeDTO, String username) throws NoPermissionException {
        
        verifyPermission(username, Config.OPERATOR);
        
        TPlace place = placeFacade.find(placeDTO.getId());
        
        if(place == null)
            return false;
        
        if(!validatePlaceDTO(placeDTO))
            return false;
        
        place.setCity(placeDTO.getCity());
        place.setCountry(placeDTO.getCountry());
        place.setTPlacefeedbackCollection(new ArrayList());
        if(placeDTO.getAddress() != null && !placeDTO.getAddress().isEmpty())
            place.setAddress(placeDTO.getAddress());
        
        placeFacade.edit(place);
        return true;
    }

    @Override
    public boolean removePlace(TPlaceDTO placeDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.OPERATOR);
        
        TPlace place = placeFacade.find(placeDTO.getId());
        
        if(place == null)
            return false;
        placeFacade.remove(place);
        return true;
    }
    
    private TPlaceDTO placeToDTO(TPlace place){
        
        System.out.println("\n\nPlace: " + place);
        List<TPlaceFeedbackDTO> placeFeedBackList = new ArrayList();
        for(TPlacefeedback placeFeedback : place.getTPlacefeedbackCollection())
        {
            placeFeedBackList.add(placeFeedbackToDTO(placeFeedback));
        }
        
        return new TPlaceDTO(place.getId(), place.getCountry(), place.getCity(), place.getAddress(),placeFeedBackList);
    }
    
    private boolean validatePlaceDTO(TPlaceDTO placeDTO)
    {
        if(placeDTO == null)
            return false;
        
        if(placeDTO.getCity() == null || placeDTO.getCity().isEmpty())
            return false;
        
        if(placeDTO.getCountry()== null || placeDTO.getCountry().isEmpty())
            return false;
        
        return true;
    }
    
    //-------------------------------------------------------------------------------------------------------------------
    //feedback
    
    @Override
    public boolean addFeedbackToPlace(TPlaceDTO placeDTO, TPlaceFeedbackDTO feedbackDTO, String username ) throws NoPermissionException {
        
        verifyPermission(username, Config.CLIENT);
        
        TPlace place = placeFacade.find(placeDTO.getId());
        
        if(place == null)
            return false;
        
        TUser user = userManager.getTUserByUsername(username);
        
        if(user == null)
            return false;
        
        if(!validatePlaceFeedbackDTO(feedbackDTO))
            return false;
        
        TPlacefeedback placeFeedback = new TPlacefeedback();
        placeFeedback.setPlaceid(place);
        placeFeedback.setUserid(user);
        placeFeedback.setScore(feedbackDTO.getScore());
        
        placeFeedbackFacade.create(placeFeedback);
        
        place.getTPlacefeedbackCollection().add(placeFeedback);
        placeFacade.edit(place);
        
        return true;
    }
    
    @Override
    public TPlaceFeedbackDTO findPlacefeedback(int id) {
        TPlacefeedback placeFeedback = placeFeedbackFacade.find(id);
        
        if(placeFeedback == null)
            return null;
        
        return placeFeedbackToDTO(placeFeedback);
    }
    
    @Override
    public boolean editFeedbackOfPlace(TPlaceFeedbackDTO feedbackDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.CLIENT);
        
        TPlacefeedback placeFeedback = placeFeedbackFacade.find(feedbackDTO.getId());
        
        if(placeFeedback == null)
            return false;
        
        //se o comentario for de um user diferente do que esta a alterar manda excecao
        if(!placeFeedback.getUserid().getUsername().equals(username))
            throw new NoPermissionException(Config.msgNoPermissionFeedback);
        
        if(!validatePlaceFeedbackDTO(feedbackDTO))
            return false;
        
        placeFeedback.setScore(feedbackDTO.getScore());
        
        placeFeedbackFacade.edit(placeFeedback);
        
        return true;
    }
    
    private TPlaceFeedbackDTO placeFeedbackToDTO(TPlacefeedback feedback){
        return new TPlaceFeedbackDTO(feedback.getId(), feedback.getScore());
    }
    
    private boolean validatePlaceFeedbackDTO(TPlaceFeedbackDTO placeFeedback)
    {
        if(placeFeedback == null)
            return false;
        
        if(placeFeedback.getScore() < 0)
            return false;
        
        return true;
    }
    
    @Override
    public boolean removeFeedbackOfPlace(TPlaceFeedbackDTO feedbackDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.CLIENT);
        
        TPlacefeedback placeFeedback = placeFeedbackFacade.find(feedbackDTO.getId());
        
        if(placeFeedback == null)
            return false;
        
        TPlace place = placeFeedback.getPlaceid();
        
        if(place == null)
            return false;
        
        //se o comentario for de um user diferente do que esta a alterar manda excecao
        if(!placeFeedback.getUserid().getUsername().equals(username))
            throw new NoPermissionException(Config.msgNoPermissionFeedback);
        
        placeFeedbackFacade.remove(placeFeedback);
        
        place.getTPlacefeedbackCollection().remove(placeFeedback);
        placeFacade.edit(place);
        
        return true;
    }
    //-----------------------------------------------------------------------------------------------------------------
    //auxiliar methods
    
    private void verifyPermission(String username, int permissionType) throws NoPermissionException{
        if(username == null || username.isEmpty())
            throw new NoPermissionException(Config.msgNoPermissionOperator);
        
        TUserDTO userDTO = userManager.getTUserDTO(username);
        
        if(userDTO == null)
            throw new NoPermissionException(Config.msgNoPermissionOperator);
        
        if(!userDTO.getAccepted())
            throw new NoPermissionException(Config.msgNoPermissionOperator);       

        //se for um cliente e a permissao exigida for do tipo de cliente permite... caso contrario nao deixa (os operadores podem fazer tudo, portanto nao fiz validacao para os operadores)
        if(userDTO.getUsertype() == Config.CLIENT && permissionType != Config.CLIENT)
            throw new NoPermissionException(Config.msgNoPermissionOperator);       
    }

    

    

    

}
