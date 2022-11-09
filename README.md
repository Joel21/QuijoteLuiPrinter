# QuijoteLuiPrinter
Aplicación para generar RIDE en formato PDF de los comprobantes electrónicos del SRI Ecuador

## Para compilar.
```
ant
```
## Comando en terminal para publicar en el repositorio Maven Local (Linux/Mac)
```
mvn install:install-file -Dfile=./dist/QuijoteLuiPrinter-1.6.jar -DgroupId=com.quijotelui.printer -DartifactId=QuijoteLuiPrinter -Dversion=1.6 -Dpackaging=jar
```
## Comando en terminal para publicar en el repositorio Maven Local (Windows)
```
cd .\dist\
mvn install:install-file "-Dfile=QuijoteLuiPrinter-1.6.jar" "-DgroupId=com.quijotelui.printer" "-DartifactId=QuijoteLuiPrinter" "-Dversion=1.6" "-Dpackaging=jar"
```
### Documentación
https://mestizos.dev/quijotelui-printer/
