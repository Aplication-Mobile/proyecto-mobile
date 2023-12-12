package com.will.bananap.entities

class cls_Comentarios {




        var userId: String = ""
        var texto: String = ""
        var datosInvestigacionId: String = ""

        // Puedes agregar más campos según tus necesidades

        constructor() {}

        constructor(userId: String, texto: String, datosInvestigacionId: String) {
            this.userId = userId
            this.texto = texto
            this.datosInvestigacionId = datosInvestigacionId
        }


}
