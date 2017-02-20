#
# E Krainz, 
# generate model form from xsd


# default values
modelfile=defaultfile
package=defaultpackage


# check parameter

if [ "$1" == "" ]; then
    echo ""
    echo "genmodel: no parameters"
    echo "usage: genmodel file [package]"
    echo ""
    echo "   file   e.g. domain.xsd "
    echo "   package   e.g. org.domain (optional)"
else
	modelfile=$1
 fi

if [ "$2" != "" ]; then
   package=$2
 fi


#echo " --- status ----"
#echo $modelfile
#echo $package
#echo ""


#generates Java classe (Model ) from xsd

if [ $modelfile != "defaultfile" ]; then
	echo "generates Java classes (model) from xsd"
	jaxb-ri/bin/xjc.sh -p $package $modelfile
fi


# optional move to * 
# mv MODEL to src
#mv $package src/