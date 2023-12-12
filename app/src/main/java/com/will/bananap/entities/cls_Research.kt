package com.will.bananap.entities

class cls_Research {
    var titulo: String = ""
    var area: String = ""
    var descripcion: String = ""
    var urlPdf: String = ""
    var urlImages: List<String> = emptyList()
    var conclusion: String = ""
    var recomendacion: String = ""
    var userId: String = ""
    var id: String = ""  // Agrega este campo para el ID del documento

    constructor()

    constructor(
        titulo: String,
        area: String,
        descripcion: String,
        urlPdf: String,
        urlImages: List<String>,
        conclusion: String,
        recomendacion: String,
        userId: String,
        id: String  // Incluye el ID en el constructor
    ) {
        this.titulo = titulo
        this.area = area
        this.descripcion = descripcion
        this.urlPdf = urlPdf
        this.urlImages = urlImages
        this.conclusion = conclusion
        this.recomendacion = recomendacion
        this.userId = userId
        this.id = id  // Asigna el ID
    }
}