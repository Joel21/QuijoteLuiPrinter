# QuijoteLuiPrinter
Aplicación para generar RIDE en formato PDF

## Para compilar con el comando.

$ ant

## Para publicar en en repositorio Maven Local.

$ mvn install:install-file -Dfile=/data/git/QuijoteLui/app/QuijoteLuiPrinter/dist/QuijoteLuiPrinter-1.1.jar -DgroupId=com.quijotelui.printer -DartifactId=QuijoteLuiPrinter -Dversion=1.1 -Dpackaging=jar