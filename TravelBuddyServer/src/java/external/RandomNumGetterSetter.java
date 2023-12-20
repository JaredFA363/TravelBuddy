/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package external;

/**
 *
 * @author jared
 */
public class RandomNumGetterSetter {
  private String status;
  private float min;
  private float max;
  private int random;


 // Getter Methods 

  public String getStatus() {
    return status;
  }

  public float getMin() {
    return min;
  }

  public float getMax() {
    return max;
  }

  public int getRandom() {
    return random;
  }

 // Setter Methods 

  public void setStatus( String status ) {
    this.status = status;
  }

  public void setMin( float min ) {
    this.min = min;
  }

  public void setMax( float max ) {
    this.max = max;
  }

  public void setRandom( int random ) {
    this.random = random;
  }
}
