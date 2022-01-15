package com.pr.purrfectrecipes.Customer

import android.util.Log
import java.io.UnsupportedEncodingException
import java.util.*
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class GMail {
    val emailPort = "587" // gmail's smtp port
    val smtpAuth = "true"
    val starttls = "true"
    val emailHost = "smtp.gmail.com"
    var fromEmail: String? = null
    var fromPassword: String? = null
    var toEmailList: List<*>? = null
    var emailSubject: String? = null
    var emailBody: String? = null
    lateinit var emailProperties: Properties
    var mailSession: Session? = null
    lateinit var emailMessage: MimeMessage

    constructor() {}
    constructor(
        fromEmail: String?, fromPassword: String?,
        toEmailList: List<*>?, emailSubject: String?, emailBody: String?
    ) {
        this.fromEmail = fromEmail
        this.fromPassword = fromPassword
        this.toEmailList = toEmailList
        this.emailSubject = emailSubject
        this.emailBody = emailBody
        emailProperties = System.getProperties()
        emailProperties["mail.smtp.port"] = emailPort
        emailProperties["mail.smtp.auth"] = smtpAuth
        emailProperties["mail.smtp.starttls.enable"] = starttls
        emailProperties.put("mail.smtp.ssl.trust", "*")

        Log.i("GMail", "Mail server properties set.")
    }

    @Throws(
        AddressException::class,
        MessagingException::class,
        UnsupportedEncodingException::class
    )
    fun createEmailMessage(): MimeMessage {
        mailSession = Session.getDefaultInstance(emailProperties, null)
        emailMessage = MimeMessage(mailSession)
        emailMessage.setFrom(InternetAddress(fromEmail, fromEmail))
        for (toEmail in toEmailList!!) {
            Log.i("GMail", "toEmail: $toEmail")
            emailMessage.addRecipient(
                Message.RecipientType.TO,
                InternetAddress(toEmail.toString())
            )
        }

        emailMessage.subject = emailSubject
        emailMessage.setContent(emailBody, "text/html") // for a html email
        Log.i("GMail", "Email Message created.")
        return emailMessage
    }

    @Throws(AddressException::class, MessagingException::class)
    fun sendEmail() {
        val transport = mailSession!!.getTransport("smtp")
        transport.connect(emailHost, fromEmail, fromPassword)
        Log.i("GMail", "allrecipients: " + emailMessage.allRecipients)
        transport.sendMessage(emailMessage, emailMessage.allRecipients)
        transport.close()
        Log.i("GMail", "Email sent successfully.")
    }

}