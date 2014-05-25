// 引用 LiquidCrystal Library
#include <LiquidCrystal.h>

// Arduino Wire library is required if I2Cdev I2CDEV_ARDUINO_WIRE implementation
// is used in I2Cdev.h
#include "Wire.h"
 
// I2Cdev and MPU6050 must be installed as libraries, or else the .cpp/.h files
// for both classes must be in the include path of your project
#include "I2Cdev.h"
#include "MPU6050.h"

#define voltage_range (1.94-1.3)
#define values_range 
#define zero_x 1.62
#define zero_y 1.62
#define zero_z 1.62
#define sensitivity_x 0.32
#define sensitivity_y 0.32
#define sensitivity_z 0.32

int up_bound,down_bound;

float xv;
float yv;
float zv;

float angle_x;
float angle_y;
float angle_z;

#define refreshRange 300




/**Object creation*/
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);

// class default I2C address is 0x68
// specific I2C addresses may be passed as a parameter here
// AD0 low = 0x68 (default for InvenSense evaluation board)
// AD0 high = 0x69
MPU6050 accelgyro;
 
int16_t ax, ay, az;
int16_t ax_up,ax_down;
int16_t gx, gy, gz;
 
float toG(float v){
  return v * 6 / 1023 - 3;
}
 
void setup() {

  //LCD Startup for 2x16 lcd screen
  lcd.begin(16, 2);

  // join I2C bus (I2Cdev library doesn't do this automatically)
  Wire.begin();
 
  // initialize serial communication
  // (38400 chosen because it works as well at 8MHz as it does at 16MHz, but
  // it's really up to you depending on your project)
  Serial.begin(9600);
 
  // initialize device
  Serial.println("Initializing I2C devices...");
  accelgyro.initialize();
 
  // verify connection
  Serial.println("Testing device connections...");
  Serial.println(accelgyro.testConnection() ? "MPU6050 connection successful" : "MPU6050 connection failed");

  // initialize up_bound down_bound
  int ox  = analogRead(A0);
  int oy  = analogRead(A1);
  int oz  = analogRead(A2);
  up_bound = ox;
  down_bound = ox;
}
 
void loop() {

  //orientation lcd print

  int ox  = analogRead(A0);
  int oy  = analogRead(A1);
  int oz  = analogRead(A2);

  xv=(((ox-278)/132.0*(0.64)+1.3)-zero_x)/sensitivity_x; // xg
  yv=(((oy-278)/132.0*(0.64)+1.3)-zero_y)/sensitivity_y; // yg
  zv=(((oz-278)/132.0*(0.64)+1.3)-zero_z)/sensitivity_z; // zg

  angle_x =atan2(-yv,-zv)*57.2957795+180;
  angle_y =atan2(-xv,-zv)*57.2957795+180;
  angle_z =atan2(-yv,-xv)*57.2957795+180;

  lcd.setCursor(0, 0);
  lcd.print("ox:");
  lcd.print(ox);
  lcd.setCursor(7, 0);
  lcd.print("xv:");
  lcd.print(xv);
  lcd.print("  ");
  lcd.setCursor(0, 1);
  lcd.print("a_x:");
  lcd.print((int)angle_x);
  lcd.print("  ");
  

  // read raw accel/gyro measurements from device
  accelgyro.getMotion6(&ax, &ay, &az, &gx, &gy, &gz);
 
  // these methods (and a few others) are also available
  //accelgyro.getAcceleration(&ax, &ay, &az);
  //accelgyro.getRotation(&gx, &gy, &gz);
 
  // display tab-separated accel/gyro x/y/z values
  Serial.print("a/g:\t");
  Serial.print(ax); 
  Serial.print("\t");
  Serial.print(ay); 
  Serial.print("\t");
  Serial.print(az); 
  Serial.print("\t");
  Serial.print(gx); 
  Serial.print("\t");
  Serial.print(gy); 
  Serial.print("\t");
  Serial.println(gz);

}
