<!--
 * Created by PhpStorm.
 * User: Eileen
 * Date: 11/17/2016
 * Time: 3:38 PM
 -->
<!DOCTYPE html>
<html lang="en">
<head>
    <title>RNA Graphics</title>
    <meta name="author" content="Eileen Rock"><script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script>
        window.onload= function () {
            console.log("It's loaded!");};
        window.onunload = function () {console.log("It's loaded!");};

        var colr = "#FFFFFF";
        var colr2 = 'e';
        var hold = 0;
        var colr3 = "black";
        var grd = [];
        var grd2 = [];
        var tst,tzt,tet = "false";
        var mxy;
        var spin = "false";

        function setColorA(){colr = "#3D3C3A"; colr2='A'; if(spin === "false"){
            document.getElementById("enumer").innerHTML = "<p>You are working with Adenine</p>";tzt="true";} else{tzt = "false";} tst,tet = "false";}
        function init(apl){grd.push(apl);}
        function setColorC(){colr = "#38ACEC"; colr2='C';if(spin === "false"){
            document.getElementById("enumer").innerHTML = "<p>You are working with Cytosine</p>";tzt="true";}else{tzt = "false";} tst,tet = "false";}
        function setColorU(){colr = "#5FFB17"; colr2='U';if(spin === "false"){
            document.getElementById("enumer").innerHTML = "<p>You are working with Uracil</p>";tzt="true";}else{tzt = "false";} tst,tet= "false";}
        function setColorG(){colr = "#FFD801"; colr2='G';if(spin === "false"){
            document.getElementById("enumer").innerHTML = "<p>You are working with Guanine</p>"; tzt="true";}else{tzt = "false";}tst,tet = "false";}
        function setColorP(){colr = "#F660AB"; colr2='P';if(spin === "false"){
            document.getElementById("enumer").innerHTML = "<p>You are working with an unknown base</p>";tzt="true";}else{tzt = "false";} tst,tet = "false";}
        function setPartner(){colr3 = "red"; document.getElementById("enumer").innerHTML = "<p>You are working with setting partner relationships</p>";
            tst = "true";tzt="false"; tet = "false";}
        function unSet(){colr = "#FFFFFF"; colr2='e'; if(spin === "false"){
            document.getElementById("enumer").innerHTML = "<p>You have chosen to deselect/erase nucleotides</p>";}
            tst,tzt ="false"; colr3= "black"; tet = "true";}


        function changeColr(bty) {
            mxy = grd.indexOf(bty);
            if(spin === "false"){if(tzt==="true"){document.getElementById(bty).style.backgroundColor = colr;
                grd2[mxy].nucl = colr2;console.log(grd2[mxy].nucl); setSave(bty);} else
            if(tst === "true"){
                if(hold !==0){
                    var x = $("#"+bty).offset();
                    console.log(x);
                    var y = $("#"+hold).offset();
                    console.log(y);
                    grd2[hold].partn = bty; grd2[mxy].partn = hold;
                    document.getElementById(bty).style.borderRightColor = colr3;
                    document.getElementById(bty).style.borderLeftColor = colr3;
                    document.getElementById(hold).style.borderRightColor = colr3;
                    document.getElementById(hold).style.borderLeftColor = colr3;
                    document.getElementById(bty).style.borderImageSource = "url(relat1.png)";
                    document.getElementById(bty).style.borderImageOutset = "" +distance(bty,hold)+"px";
                    document.getElementById(hold).style.borderImageSource = "url(relat1.png)";
                    document.getElementById(hold).style.borderImageOutset = "" +distance(bty,hold)+"px";
                    hold = 0;

                }else{if(grd2[mxy].nucl !== 'e'){hold = bty;}}}}
            else{
                rotate(bty, grd2[mxy].key);}
            if(tet === "true"){document.getElementById(bty).style.backgroundColor = colr; document.getElementById(bty).style.borderColor = colr3;
                document.getElementById(bty).style.borderImageSource = "none";
            }
        }
        function mode() { if(spin === "false"){spin = "true"; tst,tzt,tet = "false";
            document.getElementById("enumer").innerHTML = "<p>You are in click-to-change mode; the order is none, Adenosine, Cytosine, Uracil, Guanine, Wildcard</p>";}
        else{spin = "false";
            document.getElementById("enumer").innerHTML = "<p>The tool you are working with will display here.</p>";}}
        function init2(){for(var i = 0; i < grd.length; i++){
            grd2.push({nucl:'e',partn:0,succ:0,id:0,key:0});grd2[i].id = grd[i];}}
        function toString(tng){var a = grd.indexOf(tng); return "" + grd2[a].nucl + "|"+grd2[a].partn + "|" + grd2[a].succ + "|" + grd2[a].id; }

        function rotate(fcd,bll){
            var ity = grd.indexOf(fcd);
            console.log(ity);
            console.log(grd2[ity].nucl);
            tst,tzt,tet = "false";
            if(grd2[ity].nucl === 'e'){bll = 1; console.log("got here to 1"); console.log(bll);}
            if(grd2[ity].nucl === 'A' ){bll = 2; console.log("got here 2");console.log(bll);}
            if(grd2[ity].nucl === 'C'){ bll = 3; console.log("got here 3");console.log(bll);}
            if(grd2[ity].nucl === 'U'){bll=4;console.log("got here 4");console.log(bll);}
            if(grd2[ity].nucl === 'G') {bll=5;console.log("got here 5");console.log(bll);}
            if(grd2[ity].nucl === 'P') {bll=0;console.log("got here 0");console.log(bll);}
            switch (bll){
                case 0:
                    unSet();
                    document.getElementById(fcd).style.borderRightColor = colr3;
                    document.getElementById(fcd).style.borderLeftColor = colr3;
                    document.getElementById(fcd).style.backgroundColor = colr;
                    grd2[ity].nucl = colr2;
                    bll++;
                    grd2[ity].key= bll;
                    break;
                case 1:
                    setColorA();
                    document.getElementById(fcd).style.backgroundColor = colr;
                    grd2[ity].nucl = colr2;
                    bll++;
                    grd2[ity].key= bll;
                    break;
                case 2:
                    setColorC();
                    document.getElementById(fcd).style.backgroundColor = colr;
                    grd2[ity].nucl = colr2;
                    bll++;
                    grd2[ity].key= bll;console.log(grd2[ity].nucl);
                    break;
                case 3:
                    setColorU();
                    document.getElementById(fcd).style.backgroundColor = colr;
                    grd2[ity].nucl = colr2;
                    bll++;
                    grd2[ity].key= bll;console.log(grd2[ity].nucl);
                    break;
                case 4:
                    setColorG();
                    document.getElementById(fcd).style.backgroundColor = colr;
                    grd2[ity].nucl = colr2;
                    bll++;
                    grd2[ity].key= bll;console.log(grd2[ity].nucl);
                    break;
                case 5:
                    setColorP();
                    document.getElementById(fcd).style.backgroundColor = colr;
                    grd2[ity].nucl = colr2;
                    bll =0;
                    grd2[ity].key= bll;console.log(grd2[ity].nucl);
                    break;
            }}
        function prnt(){
            for (var i = 0; i < grd2.length; i++){
                // if(grd2.nucl != 'e'){
                console.log(grd2[i].nucl);
                console.log(grd2[i].id);
                console.log(grd2[i].partn);
                console.log(grd2[i].succ);
                // }
            }}
        function distance(asper, tapr){
            var obj1 = document.getElementById("asper");
            var obj2 = document.getElementById("tapr");
            Obj1Center=[obj1.offset().left+obj1.width()/2,obj1.offset().top+obj1.height()/2];
            Obj2Center=[obj2.offset().left+obj2.width()/2,obj2.offset().top+obj2.height()/2];
            return Math.sqrt( Math.pow( Obj2Center[0]-Obj1Center[0], 2)  + Math.pow( Obj2Center[1]-Obj1Center[1], 2) );}
        function setSave(aspc){mxy = grd.indexOf(aspc);document.getElementById("a"+aspc).value = toString(grd2[mxy]); }

    </script>
    <style>
        body{background-color: #03102E; color: silver;}
        .dot{border: 1px solid black; height: 8px; width: 8px; background-color: #FFFFFF; border-image-repeat: stretch;}
        #bagof{display: none;}
        .nucl{width: 100px; height: auto;}
        #enumer{position: relative;  left: 50%; background-color:rgba(100,100,100,0.6); text-align: center; border: 1px brown solid; border-radius: 2px;
            margin: 10px; padding 7px; width: auto; height: auto;}
    </style>
</head>


<body>
<?php
$coun = 0;
$count = 0;
$ice = 1;
$strn = "";
echo '<table>';
for($coun = 0; $coun < 55; $coun++){
    echo '<tr>';
    for($count = 0; $count < 75; $count++){
        echo '<td class="dot '.$count.'" id="'.$ice.'" onclick="changeColr('.$ice.');"></td>';
        echo '<script>init('.$ice.');</script>';
        $ice++;}
    echo '</tr>';}
echo '</table>';
?>
<form id="bagof">
    <?php
    $coun = 0;
    for($coun = 0; $coun < $ice; $coun++){
        echo '<input type="text" name="'.$coun.'" value="0">';
    }
    ?>
</form>
<table id="tools" border="0"><tr><td id="nuclA" class="nucl"><button type="button" onclick="setColorA()">Adenine</button></td>
        <td id="nuclC" class="nucl"><button type="button" onclick="setColorC()">Cytosine</button></td>
        <td id="nuclU" class="nucl"><button type="button" onclick="setColorU()">Uracil</button></td>
        <td id="nuclG" class="nucl"><button type="button" onclick="setColorG()">Guanine</button></td>
        <td id="nuclP" class="nucl"><button type="button" onclick="setColorP()">Unknown</button></td>
        <td id="parten" class="nucl"><button type="button" onclick="setPartner()">Partner Up</button></td>
        <td id="era" class="nucl"><button type="button" onclick="unSet()">Erase</button></td>
        <td><button type="button" onclick="prnt()">Check</button> </td>
    </tr><tr><td>-------</td></tr><tr><td><button type="button" onclick="mode()" class="nucl">Switch selection mode</button> </td><td colspan="7" id="enumer"><p>The tool you are working with will display here.</p></td></tr></table>

<script>init2();</script>
</body>
</html>