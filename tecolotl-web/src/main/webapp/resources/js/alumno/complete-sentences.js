var cuenta = [];
var cadena = [];
var first = [], answ = [],identDrags = [],cajaCardinalidad = [];
var dragSen,respuestasDrag4;
var cajaAnswer;

document.addEventListener('DOMContentLoaded', function (evt) {
    evitarPreguntas();
    // seleccionSiguiente();
    document.querySelector('li[uk-filter-control=".complete-sentences"]').addEventListener('click' ,function () {
        console.log('entro');
        let botonPosicion = document.querySelector('#colocar2');
        setTimeout(function(){ botonPosicion.click(); }, 1000)
    });
});


function evitarPreguntas() {
    dragSen = UIkit.util.$$('.complete-sentences form .respuesta-ordenar-oracion');
    respuestasDrag4 = UIkit.util.$$('.complete-sentences .contedor-oraciones ');
    for (var inx = 0;inx < dragSen.length;inx++ ){
        cuenta.push(0);
    }
    UIkit.util.on(dragSen,'added',function (texto) {
        console.log(texto.target.dataset.indice);

        dragSen.forEach(function (caja) {
            if(caja.innerText === ""){
                cuenta[caja.dataset.indice] = 0;
                // console.log(caja);
            }
            if(caja.dataset.indice === texto.target.dataset.indice ){
                cuenta[caja.dataset.indice]++;
                if (cuenta[caja.dataset.indice] === 1){
                    first[caja.dataset.indice] = dragSen[texto.target.dataset.indice].querySelector('span').textContent;
                    answ[caja.dataset.indice] = dragSen[texto.target.dataset.indice].querySelector('span').dataset.index;
                    identDrags[caja.dataset.indice] = dragSen[texto.target.dataset.indice].querySelector('span').id;
                    cajaCardinalidad[caja.dataset.indice] = dragSen[texto.target.dataset.indice].querySelector('span').dataset.cardinalidad;
                }
                if(cuenta[caja.dataset.indice] === 2){
                    var textos = dragSen[texto.target.dataset.indice].querySelectorAll('span');
                    console.log(textos);
                    var cajaTexto = dragSen[texto.target.dataset.indice];
                    var cajaResp = document.querySelector('.complete-sentences .contedor-oraciones');
                    var caja2 = document.createElement('span');
                    var caja3 = document.createElement('span');
                    textos.forEach(function (word) {
                        var palabra;
                        if (word.dataset.index !== answ[caja.dataset.indice]){
                            caja2.innerHTML = word.innerHTML;
                            console.log(word.dataset.index + " " + answ[caja.dataset.indice]);
                            while (cajaTexto.hasChildNodes()){
                                cajaTexto.removeChild(cajaTexto.firstChild);
                            }
                            caja3.innerHTML = first[caja.dataset.indice];
                            // caja2.classList.add('palabra-imagen');
                            caja2.id = word.id;
                            caja2.dataset.index = word.dataset.index;
                            caja2.dataset.cardinalidad = word.dataset.cardinalidad;
                            cajaTexto.appendChild(caja2);
                            // caja3.classList.add('palabra-imagen');
                            caja3.id = identDrags[caja.dataset.indice];
                            caja3.dataset.index = answ[caja.dataset.indice];
                            caja3.dataset.cardinalidad = cajaCardinalidad[caja.dataset.indice];
                            cajaResp.appendChild(caja3);
                            console.log(first[caja.dataset.indice]);
                        }

                    });
                    cuenta[caja.dataset.indice] = 1;
                    first[caja.dataset.indice] = dragSen[texto.target.dataset.indice].textContent;
                    answ[caja.dataset.indice] = dragSen[texto.target.dataset.indice].querySelector('span').dataset.index;
                    cajaCardinalidad[caja.dataset.indice] = dragSen[texto.target.dataset.indice].querySelector('span').dataset.cardinalidad;
                    identDrags[caja.dataset.indice] = dragSen[texto.target.dataset.indice].querySelector('span').id;

                }
            }
        });

    });
    UIkit.util.on(respuestasDrag4,'added', function () {
        console.log('agregado');
        dragSen.forEach(function (box,indice) {
            if (box.innerText === "") {
                cuenta[indice] = 0;
                // console.log(indice);
            }
        });
    });
}

// function seleccionSiguiente() {
//     document.querySelector('.complete-sentences form ul li.boton-falta').click();
// }