/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import java.util.List;
import javax.ejb.Local;
import logic.NoPermissionException;
import logic.TAirlineDTO;
import logic.TPlaceFeedbackDTO;
import logic.TPlaceDTO;
import logic.TPlaneDTO;

/**
 *
 * @author bruno
 */
@Local
public interface TripsManagerLocal {
    
    //planes
    List<TPlaneDTO> findAllPlanes(String username) throws NoPermissionException;
    TPlaneDTO findPlane(int id, String username) throws NoPermissionException;
    boolean addPlane(TPlaneDTO planeDTO, String username) throws NoPermissionException;
    boolean editPlane(TPlaneDTO planeDTO, String username) throws NoPermissionException;
    boolean removePlane(TPlaneDTO planeDTO, String username) throws NoPermissionException;
    
    //Airline
    List<TAirlineDTO> findAllAirlines(String username) throws NoPermissionException;
    TAirlineDTO findAirline(int id, String username) throws NoPermissionException;
    boolean addAirline(TAirlineDTO airlineDTO, String username) throws NoPermissionException;
    boolean editAirline(TAirlineDTO airlineDTO, String username) throws NoPermissionException;
    boolean removeAirline(TAirlineDTO airlineDTO, String username) throws NoPermissionException;
    
    //places
    List<TPlaceDTO> findAllPlaces(String username);
    TPlaceDTO findPlace(int id);
    boolean addPlace(TPlaceDTO placeDTO, String username) throws NoPermissionException;
    boolean editPlace(TPlaceDTO placeDTO, String username) throws NoPermissionException;
    boolean removePlace(TPlaceDTO placeDTO, String username) throws NoPermissionException;
    
    //place feedback
    TPlaceFeedbackDTO findPlacefeedback(int id);
    boolean addFeedbackToPlace(TPlaceDTO placeDTO, TPlaceFeedbackDTO feedbackDTO, String username) throws NoPermissionException;
    boolean editFeedbackOfPlace(TPlaceFeedbackDTO feedbackDTO, String username) throws NoPermissionException;
    boolean removeFeedbackOfPlace(TPlaceFeedbackDTO feedbackDTO, String username) throws NoPermissionException;

}
