package tk.aimeschneider.classroomquizz.ModelOnly;

public class TemperatureSensorData {

    private double temperature;
    private int humidity;
    private int power;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public static TemperatureSensorData parseData(String[] bytes) {

        double temperature = Integer.valueOf(bytes[2] + bytes[1], 16).shortValue() / 10.0;
        int humidity = Integer.parseInt(bytes[4], 16);
        int battery = Integer.parseInt(bytes[9], 16);

        TemperatureSensorData sensorData = new TemperatureSensorData();
        sensorData.setTemperature(temperature);
        sensorData.setHumidity(humidity);
        sensorData.setPower(battery);
        return sensorData;
    }
}

/*
 * @startuml
 * class TemperatureSensorData{
 *   temperature : Double
 *   humidity : Integer
 *   power : Integer
 * }
 * @enduml
 */
