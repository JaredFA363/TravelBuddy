/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package external;

/**
 *
 * @author jared
 */
public class checkWeatherData {
  private String location_to;
  private String date_from;
  private String date_to;


 // Getter Methods 

  public String getLocation_to() {
    return location_to;
  }

  public String getDate_from() {
    return date_from;
  }

  public String getDate_to() {
    return date_to;
  }

 // Setter Methods 

  public void setLocation_to( String location_to ) {
    this.location_to = location_to;
  }

  public void setDate_from( String date_from ) {
    this.date_from = date_from;
  }

  public void setDate_to( String date_to ) {
    this.date_to = date_to;
  }
}
