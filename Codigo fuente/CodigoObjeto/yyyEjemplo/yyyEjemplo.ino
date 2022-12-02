        #include <Servo.h>
//#########################################################
//                CONFIGURACION
//
// Declaramos las variables para controlar los servos.
// Puerta recepción.
Servo serPrincipal;
// Puerta sala 1.
Servo serSala1;
// Puerta sala 2.
Servo serSala2;
// Aspersor.
Servo serAspersor;
// Panel.
Servo serPanel;

// Variable para el movimiento del Aspersor.
int pos = 0;
// Asignamos los puertos a los LEDs y Servomotores.
const int ledRecepcion = 2;
const int ledPrincipal = 3;
const int ledSala1 = 4;
const int ledSala2 = 5;
const int motorVentilador = 7;
const int servoPrincipal = 8;
const int servoSala1 = 9;
const int servoSala2 = 10;
const int servoAspersor = 11;
const int servoPanel = 12;

void iluminacion_desactivar(int led);
void iluminacion_activar(int led);
void puerta_abrir(int puerta);
void puerta_cerrar(int puerta);
void ventilador_activar();
void ventilador_desactivar();
void panel_encender();
void panel_apagar();
void panel_girar(int grados);
void aspersor_activar();
void aspersor_desactivar();
//#########################################################

int x;
int veces;
int y;

void setup() {
  // Iniciamos el monitor serie para mostrar el resultado.
  Serial.begin(9600);

  // Asignamos los servomotores a los puertos.
  serPrincipal.attach(servoPrincipal);
  serSala1.attach(servoSala1);
  serSala2.attach(servoSala2);
  serAspersor.attach(servoAspersor);
  serPanel.attach(servoPanel);

  // Definimos los pines como salida.
  pinMode(ledRecepcion, OUTPUT);
  pinMode(ledPrincipal, OUTPUT);
  pinMode(ledSala1, OUTPUT);
  pinMode(ledSala2, OUTPUT);
  pinMode(motorVentilador, OUTPUT);

  digitalWrite(ledRecepcion, LOW);
  digitalWrite(ledPrincipal, LOW);
  digitalWrite(ledSala1, LOW);
  digitalWrite(ledSala2, LOW);
  digitalWrite(motorVentilador, LOW);

  // Puerta principal (90 cerrado y 150 abierto).
  serPrincipal.write(90);
  // Puerta sala 1 (10 cerrado y 70 abierto).
  serSala1.write(10);
  // Puerta sala 2 (0 cerrado y 80 abierto).
  serSala2.write(0);
  // Panel (rango de 0 a 180).
  serPanel.write(0);

  // Esperamos 1 segundo.
  delay(1000);

  //INICIO CODIGO GENERADO EN COMPILADOR

 x=2;
 veces=5;
 y=4;
 if(y>=veces){
aspersor_activar();

}
 for(int i = 0; i<5; i++){
panel_encender();

panel_girar(120);

}
puerta_abrir(2);


}
void loop() {
// Código que se repite:

}
/*
###############################################
ILUMINACION

1 --> Recepcion
2 --> Principal
3 --> Sala1
4 --> Sala2

PUERTA
1 --> Recepcion
2 --> Sala1
3 --> Sala2
###############################################

*/

void iluminacion_desactivar(int led) {
  switch (led) {
    case 1:
      digitalWrite(ledRecepcion, LOW);
      break;

    case 2:
      digitalWrite(ledPrincipal, LOW);
      break;
    case 3:
      digitalWrite(ledSala1, LOW);
      break;
    case 4:
      digitalWrite(ledSala2, LOW);
      break;
  }
  delay(250);
}
void iluminacion_activar(int led) {
  switch (led) {
    case 1:
      digitalWrite(ledRecepcion, HIGH);
      break;

    case 2:
      digitalWrite(ledPrincipal, HIGH);
      break;
    case 3:
      digitalWrite(ledSala1, HIGH);
      break;
    case 4:
      digitalWrite(ledSala2, HIGH);
      break;
  }
  delay(250);
}
void puerta_abrir(int puerta) {
  switch (puerta) {
    case 1:
      // Puerta principal (90 cerrado y 150 abierto).
      serPrincipal.write(150);
      break;
    case 2:
      // Puerta sala 1 (10 cerrado y 70 abierto).
      serSala1.write(70);
      break;
    case 3:
      // Puerta sala 2 (0 cerrado y 80 abierto).
      serSala2.write(80);
      break;
  }
  delay(250);
}
void puerta_cerrar(int puerta) {
  switch (puerta) {
    case 1:
      // Puerta principal (90 cerrado y 150 abierto).
      serPrincipal.write(90);
      break;
    case 2:
      // Puerta sala 1 (10 cerrado y 70 abierto).
      serSala1.write(10);
      break;
    case 3:
      // Puerta sala 2 (0 cerrado y 80 abierto).
      serSala2.write(0);
      break;
  }
  delay(250);
}
void ventilador_activar() {
  digitalWrite(motorVentilador, HIGH);
  delay(1000);
}
void ventilador_desactivar() {
  digitalWrite(motorVentilador, LOW);
  delay(1000);
}
void panel_encender() {
  // Panel (rango de 0 a 180).
  serPanel.write(90);
  delay(250);
}
void panel_apagar() {
  // Panel (rango de 0 a 180).
  serPanel.write(0);
  delay(250);
}
void panel_girar(int grados) {
  // Panel (rango de 0 a 180).
  serPanel.write(grados);
  delay(250);
}
void aspersor_activar() {
  // Aspersor.
  for (pos = 0; pos <= 180; pos += 10) {
    serAspersor.write(pos);
    delay(250);
  }


  for (pos = 180; pos <= 0; pos += 10) {
    serAspersor.write(pos);
    delay(250);
  }
}
void aspersor_desactivar() {
  serAspersor.write(0);
  delay(250);
}