var modalProfresor;
var modalEliminar;

document.addEventListener('DOMContentLoaded', ejecucionInicio);

function ejecucionInicio(evento) {
    modalProfresor = document.getElementById('modal-profesor');
    modalEliminar = document.getElementById('modal-eliminar');
    var imagenesContra = document.querySelectorAll('#formulario-modal-profesor .uk-modal-body > div > div:last-child > img');
    imagenesContra.forEach(img => {
       img.addEventListener('click',() => {
            img.classList.toggle("seleccionado");
       });
    });
    abrirModal();
    elimina();
}

function cierra(evento) {
    cerrarModal(null, "modalProfresor");
    cerrarModal(null, "modalGrupo");
}

function elimina() {
    modalProfresor.querySelector('.uk-form-controls select').removeAttribute("size");
}

function cerrarModal(data , modal) {
    if (data === null) {
        switch (modal) {
            case 'modalProfresor' :
                if (modalProfresor.querySelector('.uk-text-danger') === null) {
                    UIkit.modal(modalProfresor).hide();
                }
                break;
            case 'modal-eliminar':
                UIkit.modal(modalEliminar).hide();
                break;
            default :
                console.error('Aun no se programa este modal:' + modal);
        }
    } else {
        if (data.status === "success") {
            switch (modal) {
                case 'modalProfresor' :
                    if (modalProfresor.querySelector('.uk-text-danger') === null) {
                        UIkit.modal(modalProfresor).hide();
                    }
                    break;
                case 'modal-eliminar':
                    UIkit.modal(modalEliminar).hide();
                    break;
                default :
                    console.error('Aun no se programa este modal:' + modal);
            }
        }
    }
}


function abrirModal() {

    UIkit.util.on('#modal-profesor', 'shown', function () {
        var modal = document.querySelector('#modal-profesor');
        let contrasena = modal.querySelector('.uk-modal-body input[type=hidden]').value;
        let imagenes = document.querySelectorAll('#formulario-modal-profesor .uk-modal-body > div > div:last-child > img');
        if(contrasena !== ""){
            let password = contrasena.split(',');
            imagenes[parseInt(password[0])].classList.add("seleccionado");
            imagenes[parseInt(password[1])].classList.add("seleccionado");
        }

        modalProfresor.querySelector('.uk-modal-header').click();
        modalProfresor.querySelector('.uk-form-controls select').removeAttribute("size");
        seleccionContrasena(imagenes);

    });
    UIkit.util.on('#modal-eliminar', 'show', function () {
        modalEliminar.querySelector('.uk-modal-header').click();
    });
    UIkit.util.on('#modal-profesor', 'hidden', function () {
        modalProfresor.querySelectorAll('input').forEach(entrada => entrada.value = '');
    });
}

function cargaContrasenia() {
    console.log('entre');
    var imagenes = document.querySelectorAll('#formulario-modal-profesor .uk-modal-body > div > div:last-child > img');
    var seleccionados = [];
    for (i = 0; i < imagenes.length; i++) {
        if (imagenes[i].classList.contains('seleccionado')) {
            seleccionados.push(i);
        }
    }
    document.getElementById('formulario-modal-profesor:input-secret-password').value = seleccionados.length === 0 ? null : seleccionados.join(',');
}

function seleccionContrasena(img) {
    let contador = 0;
    img.forEach(image => {
        image.addEventListener('click',() => {
            contador++;
            if (contador === 1){
                img.forEach(im => {
                    im.classList.remove('seleccionado');
                })
            }
          image.classList.toggle('seleccionado');
        })
    });
}