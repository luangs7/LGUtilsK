package br.com.luan2.lgutilsk.utils

/**
 * Created by squarebits on 19/04/18.
 */

 val pesoCPF = intArrayOf(11, 10, 9, 8, 7, 6, 5, 4, 3, 2)
 val pesoCNPJ = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)

fun String.isValid(): Boolean {
    var cnpj_cpf = this

    if (cnpj_cpf == "NA") {
        return true
    }

    cnpj_cpf = cnpj_cpf.replace("\\D+".toRegex(), "")

    if (cnpj_cpf.length == 11) {
        return cnpj_cpf.isValidCPF()
    } else if (cnpj_cpf.length == 14) {
        return cnpj_cpf.isValidCNPJ()
    }
    return false
}

 fun String.isValidCPF(): Boolean {
    if (this == "00000000000" || this == "11111111111" ||
            this == "22222222222" || this == "33333333333" ||
            this == "44444444444" || this == "55555555555" ||
            this == "66666666666" || this == "77777777777" ||
            this == "88888888888" || this == "99999999999" ||
            this == null || this.length != 11)
        return false

    val digito1 = calcularDigito(this.substring(0, 9), pesoCPF)
    val digito2 = calcularDigito(this.substring(0, 9) + digito1, pesoCPF)
    return this == this.substring(0, 9) + digito1.toString() + digito2.toString()
}

fun String.isValidCNPJ(): Boolean {
    val cnpj = this
    if (cnpj == "00000000000000" || cnpj == "11111111111111" ||
            cnpj == "22222222222222" || cnpj == "33333333333333" ||
            cnpj == "44444444444444" || cnpj == "55555555555555" ||
            cnpj == "66666666666666" || cnpj == "77777777777777" ||
            cnpj == "88888888888888" || cnpj == "99999999999999" ||
            cnpj == null || cnpj.length != 14)
        return false

    val digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ)
    val digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ)
    return cnpj == cnpj.substring(0, 12) + digito1.toString() + digito2.toString()
}

private fun calcularDigito(str: String, peso: IntArray): Int {
    var soma = 0
    var indice = str.length - 1
    var digito: Int
    while (indice >= 0) {
        digito = Integer.parseInt(str.substring(indice, indice + 1))
        soma += digito * peso[peso.size - str.length + indice]
        indice--
    }
    soma = 11 - soma % 11
    return if (soma > 9) 0 else soma
}
