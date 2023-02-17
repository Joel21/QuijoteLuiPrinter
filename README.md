# QuijoteLuiPrinter
Aplicación para generar RIDE en formato PDF de los comprobantes electrónicos del SRI Ecuador

## Para compilar.
```
ant
```
o si aparece un error parecido a "The J2SE Platform is not correctly set up."
```
ant -Dplatforms.JDK_1.8.home=%JAVA_HOME%
```
## Comando en terminal para publicar en el repositorio Maven Local (Linux/Mac)
```
mvn install:install-file -Dfile=./dist/QuijoteLuiPrinter-1.8.jar -DgroupId=com.quijotelui.printer -DartifactId=QuijoteLuiPrinter -Dversion=1.8 -Dpackaging=jar
```
## Comando en terminal para publicar en el repositorio Maven Local (Windows)
```
cd .\dist\
mvn install:install-file "-Dfile=QuijoteLuiPrinter-1.8.jar" "-DgroupId=com.quijotelui.printer" "-DartifactId=QuijoteLuiPrinter" "-Dversion=1.8" "-Dpackaging=jar"
```
### Documentación
https://mestizos.dev/quijotelui-printer/
