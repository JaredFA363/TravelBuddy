/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package external;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jared
 */
public class WeatherGetterSetter {
  private String product;
  private String init;
  //ArrayList<Object> dataseries = new ArrayList<Object>();
  List<dataseries> dataseries;


 // Getter Methods 

  public String getProduct() {
    return product;
  }

  public String getInit() {
    return init;
  }
  
  public List<dataseries> getDataseries() {
    return dataseries;
  }

  public void setDataseries(List<dataseries> dataseries) {
    this.dataseries = dataseries;
  }

 // Setter Methods 

  public void setProduct( String product ) {
    this.product = product;
  }

  public void setInit( String init ) {
    this.init = init;
  }
//}

    public class dataseries {
        private Integer date;
        private String weather;
        private Temp2m temp2m;
        private Integer wind10mMax;

        public Integer getDate() {
        return date;
        }

        public void setDate(Integer date) {
        this.date = date;
        }

        public String getWeather() {
        return weather;
        }

        public void setWeather(String weather) {
        this.weather = weather;
        }

        public Temp2m getTemp2m() {
        return temp2m;
        }

        public void setTemp2m(Temp2m temp2m) {
        this.temp2m = temp2m;
        }

        public Integer getWind10mMax() {
        return wind10mMax;
        }

        public void setWind10mMax(Integer wind10mMax) {
        this.wind10mMax = wind10mMax;
        }
    
    
        public class Temp2m {

            private Integer max;

            private Integer min;

            public Integer getMax() {
            return max;
            }

            public void setMax(Integer max) {
            this.max = max;
            }

            public Integer getMin() {
            return min;
            }

            public void setMin(Integer min) {
            this.min = min;
            }

        }
    
    }
}