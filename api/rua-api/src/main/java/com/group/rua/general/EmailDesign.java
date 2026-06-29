package com.group.rua.general;

/**
 * Diseño con codigo HTML para el envio de correos
 */
public final class EmailDesign {

    public static String createDesign(String link) {

        return """
        <!DOCTYPE html>
        <html lang="es">
        <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <title>Confirmar identidad RUA</title>
        </head>

        <body style="margin:0;padding:0;background-color:#f3f7f4;font-family:Segoe UI,Tahoma,Geneva,Verdana,sans-serif;">

          <table width="100%%" border="0" cellspacing="0" cellpadding="0" style="background-color:#f3f7f4;">
            <tr>
              <td align="center" style="padding:40px 10px;">

                <table width="500" border="0" cellspacing="0" cellpadding="0"
                       style="background-color:#ffffff;border-radius:4px;overflow:hidden;">

                  <tr>
                    <td bgcolor="#1e6f36" align="center" style="padding:25px 20px;">

                      <span style="display:inline-block;width:14px;height:14px;background-color:#83e2a7;border-radius:50%%;"></span>

                      <span style="color:#ffffff;font-size:28px;font-weight:bold;margin-left:10px;">
                        RUA
                      </span>

                    </td>
                  </tr>

                  <tr>
                    <td style="padding:40px 35px;color:#333333;">

                      <h1 style="margin:0 0 15px 0;font-size:22px;color:#111111;">
                        ¡Hola!
                      </h1>

                      <p style="margin:0 0 30px 0;font-size:16px;line-height:1.5;color:#555555;">
                        Por favor confirma tu identidad en el sistema RUA.
                      </p>

                      <table width="100%%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td align="center">

                            <a href="%s"
                               target="_blank"
                               style="
                                 display:inline-block;
                                 padding:14px 35px;
                                 background:#238c47;
                                 color:#ffffff;
                                 text-decoration:none;
                                 border-radius:8px;
                                 font-size:16px;
                                 font-weight:bold;
                               ">
                               Confirmar identidad
                            </a>

                          </td>
                        </tr>
                      </table>

                      <div style="
                        margin-top:30px;
                        background:#f4f9f5;
                        border-radius:8px;
                        padding:20px;
                        font-size:14px;
                        color:#666666;
                      ">
                        Si no solicitaste esta confirmación, ignora este correo.
                        <br><br>
                        <strong>El equipo RUA</strong>
                      </div>

                    </td>
                  </tr>

                </table>

              </td>
            </tr>
          </table>

        </body>
        </html>
        """.formatted(link);
    }
}