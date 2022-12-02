
int x = 5;
String y = "Cerrado";

void setup() {
// Código que se ejecuta una vez:
Serial.begin(9600);
      
        def #pSala1  
        def #aspPatio  
        def #panelPatio  
$x = 5  
$y = "Cerrado"  
        puerta_abrir(#pSala1)  
        aspersor_activar(#aspPatio)  
        panel_encender(#panelPatio)  
        panel_girar(#panelPatio,10)    
}
void loop() {
// Código que se repite:

}