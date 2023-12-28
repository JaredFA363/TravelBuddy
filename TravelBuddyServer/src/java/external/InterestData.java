/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package external;

/**
 *
 * @author jared
 */
public class InterestData {
  private int trip_id;
  private int proposed_user_id;
  private int new_user_id;


 // Getter Methods 

  public int getTrip_id() {
    return trip_id;
  }

  public int getProposed_user_id() {
    return proposed_user_id;
  }

  public int getNew_user_id() {
    return new_user_id;
  }

 // Setter Methods 

  public void setTrip_id( int trip_id ) {
    this.trip_id = trip_id;
  }

  public void setProposed_user_id( int proposed_user_id ) {
    this.proposed_user_id = proposed_user_id;
  }

  public void setNew_user_id( int new_user_id ) {
    this.new_user_id = new_user_id;
  }
}
