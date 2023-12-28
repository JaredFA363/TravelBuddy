/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package external;

/**
 *
 * @author jared
 */
public class ForecastDay {
    String date;
    Day day;

    static class Day {
        double avgtemp_c;
        Condition condition;

        static class Condition {
            String text;
        }
    }
}
