/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package external;

/**
 *
 * @author jared
 */
public class TripData {
  private int userId;
  private String dateFrom;
  private String dateTo;
  private String locationFrom;
  private String locationTo;


 // Getter Methods 
  public int getUserId() {
    return userId;
  }

  public String getDateFrom() {
    return dateFrom;
  }

  public String getDateTo() {
    return dateTo;
  }

  public String getLocationFrom() {
    return locationFrom;
  }

  public String getLocationTo() {
    return locationTo;
  }

 // Setter Methods 
  public void setUserId( int userId ) {
    this.userId = userId;
  }

  public void setDateFrom( String dateFrom ) {
    this.dateFrom = dateFrom;
  }

  public void setDateTo( String dateTo ) {
    this.dateTo = dateTo;
  }

  public void setLocationFrom( String locationFrom ) {
    this.locationFrom = locationFrom;
  }

  public void setLocationTo( String locationTo ) {
    this.locationTo = locationTo;
  }

}
