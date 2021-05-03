package com.vspk.bookrest.event;

import com.vspk.bookrest.domain.Verification;
import com.vspk.bookrest.email.EmailService;
import com.vspk.bookrest.repository.UserRepository;
import com.vspk.bookrest.repository.VerificationRepository;
import com.vspk.bookrest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
public class UserRegisteredEventListener implements ApplicationListener<SendingEmailConfirmationEvent> {

    private final EmailService emailService;
    private final VerificationRepository verificationRepository;
    private final UserService userService;

    @Async
    @Override
    public void onApplicationEvent(SendingEmailConfirmationEvent sendingEmailConfirmationEvent) {
        var registeredUser = sendingEmailConfirmationEvent.getRegisteredUser();
        var secretToken = UUID.randomUUID().toString();
        var verificationLink = "https://bookrest1.herokuapp.com/api/v1/auth/email-verification/%s/verify".formatted(secretToken);
        var verification = new Verification(registeredUser, secretToken, tomorrow(), null);
//        String emailMessage = """
//                <html>
//
//                    <head>
//                        <meta charset="UTF-8">
//                        <meta content="width=device-width, initial-scale=1" name="viewport">
//                        <meta content="telephone=no" name="format-detection">
//                        <title></title>
//
//                    </head>
//
//                <body>
//                    <div class="es-wrapper-color">
//                        <table class="es-wrapper" width="100%" cellspacing="0" cellpadding="0">
//                            <tbody>
//                                <tr>
//                                    <td class="esd-email-paddings" valign="top">
//                                        <table class="es-content esd-footer-popover" cellspacing="0" cellpadding="0" align="center">
//                                            <tbody>
//                                                <tr>
//                                                    <td class="esd-stripe" esd-custom-block-id="3109" align="center">
//                                                        <table class="es-content-body" style="background-color: #ffffff;" width="600" cellspacing="0" cellpadding="0" bgcolor="#ffffff" align="center">
//                                                            <tbody>
//                                                                <tr>
//                                                                    <td class="esd-structure es-p20t es-p20b es-p40r es-p40l" esd-general-paddings-checked="true" align="left">
//                                                                        <table width="100%" cellspacing="0" cellpadding="0">
//                                                                            <tbody>
//                                                                                <tr>
//                                                                                    <td class="esd-container-frame" width="520" valign="top" align="center">
//                                                                                        <table width="100%" cellspacing="0" cellpadding="0">
//                                                                                            <tbody>
//                                                                                                <tr>
//                                                                                                    <td class="esd-block-text" align="left">
//                                                                                                        <h1 style="color: #4a7eb0;">Bookrest email verification</h1>
//                                                                                                    </td>
//                                                                                                </tr>
//                                                                                                <tr>
//                                                                                                    <td class="esd-block-spacer es-p5t es-p20b" align="left" style="font-size:0">
//                                                                                                        <table width="5%" height="100%" cellspacing="0" cellpadding="0" border="0">
//                                                                                                            <tbody>
//                                                                                                                <tr>
//                                                                                                                    <td style="border-bottom: 2px solid #999999; background: rgba(0, 0, 0, 0) none repeat scroll 0% 0%; height: 1px; width: 100%; margin: 0px;"></td>
//                                                                                                                </tr>
//                                                                                                            </tbody>
//                                                                                                        </table>
//                                                                                                    </td>
//                                                                                                </tr>
//                                                                                                <tr>
//                                                                                                    <td class="esd-block-text es-p10b" align="left">
//                                                                                                        <p><span style="font-size: 16px; line-height: 150%;">Hi,</span></p>
//                                                                                                    </td>
//                                                                                                </tr>
//                                                                                                <tr>
//                                                                                                    <td class="esd-block-text" align="left">
//                                                                                                        <p>We've linked this email address to your bookrest account, as you asked. Your email will be used if you forgot your password and for important account message. Your email address must be verified.</p>
//                                                                                                    </td>
//                                                                                                </tr>
//                                                                                                <tr>
//                                                                                                    <td class="esd-block-button es-p20t es-p20b" align="left"><span class="es-button-border"><a href="https://bookrest1.herokuapp.com/api/v1/auth/email-verification/%s/verify" class="es-button es-button-1619946895978" target="_blank" style="border-width: 10px 25px;">Verify email</a></span></td>
//                                                                                                </tr>
//                                                                                                <tr>
//                                                                                                    <td class="esd-block-text" align="left">
//                                                                                                         <p>If you need help, contact us - <a href="bookrestapp@gmail.com">bookrestapp@gmail.com</a></p>
//                                                                                                    </td>
//                                                                                                </tr>
//                                                                                            </tbody>
//                                                                                        </table>
//                                                                                    </td>
//                                                                                </tr>
//                                                                            </tbody>
//                                                                        </table>
//                                                                    </td>
//                                                                </tr>
//                                                            </tbody>
//                                                        </table>
//                                                    </td>
//                                                </tr>
//                                            </tbody>
//                                        </table>
//                                    </td>
//                                </tr>
//                            </tbody>
//                        </table>
//                    </div>
//                </body>
//
//                </html>
//                """.formatted(secretToken);

        String message = """
                        <html>
                              <body>
                                    <p>We've linked this email address to your bookrest account, as you asked. Your email will be used if you forgot your password and for important account message. Your email address must be verified.</p>
                                          %s
                             </body>
                       </html>
                """.formatted(verificationLink);
        verificationRepository.save(verification);
        userService.incrementVerificationTimesAsked(registeredUser.getId());
        emailService.send(registeredUser.getEmail(), "BookRest Account Verification", message/*verificationLink.formatted(secretToken)*/);

    }

    private Date tomorrow(){
        //better to use LocalDateTime from java.time package, need to migrate later
        var localDateTime = LocalDateTime.now().plusDays(1);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
