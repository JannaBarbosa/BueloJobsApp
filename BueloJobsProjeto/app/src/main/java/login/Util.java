package login;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by mac on 09/05/2018.
 */

public class Util {



  
    public static boolean verificarInternet(Context context){


        boolean status = false;
        ConnectivityManager conexao = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conexao != null){

            // PARA DISPOSTIVOS NOVOS
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                NetworkCapabilities recursosRede = conexao.getNetworkCapabilities(conexao.getActiveNetwork());

                if (recursosRede != null) {//VERIFICAMOS SE RECUPERAMOS ALGO

                    if (recursosRede.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {

                        //VERIFICAMOS SE DISPOSITIVO TEM 3G
                        return true;

                    }
                    else if (recursosRede.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

                        //VERIFICAMOS SE DISPOSITIVO TEM WIFFI
                        return true;

                    }

                    //NÃO POSSUI UMA CONEXAO DE REDE VÁLIDA

                    return false;

                }

            } else {//COMECO DO ELSE

                // PARA DISPOSTIVOS ANTIGOS  (PRECAUÇÃO)         MESMO CODIGO
                NetworkInfo informacao = conexao.getActiveNetworkInfo();


                if (informacao != null && informacao.isConnected()) {
                    status = true;
                } else
                    status = false;

                return status;

            }//FIM DO ELSE
        }



        return false;


    }





    public static void opcoesErro(Context context, String resposta){

        if (resposta.contains("least 6 characters")){

            Toast.makeText(context,"Digite uma senha maior que 5 characters",Toast.LENGTH_LONG).show();

        }
        else if(resposta.contains("address is badly")){

            Toast.makeText(context,"E-mail inválido",Toast.LENGTH_LONG).show();

        }
        else if(resposta.contains("interrupted connection")){

            Toast.makeText(context,"Sem conexão com o Firebase",Toast.LENGTH_LONG).show();

        }else if(resposta.contains("password is invalid")){

            Toast.makeText(context,"senha inválida",Toast.LENGTH_LONG).show();

        } else if(resposta.contains("There is no user")){

            Toast.makeText(context,"Este e-mail não está cadastrado",Toast.LENGTH_LONG).show();

        }
        else if(resposta.contains("address is already")){

            Toast.makeText(context," Este e-mail já foi cadastrado",Toast.LENGTH_LONG).show();

        }
        else if(resposta.contains("INVALID_EMAIL")){

            Toast.makeText(context,"E-mail inválido",Toast.LENGTH_LONG).show();

        }
        else if(resposta.contains("EMAIL_NOT_FOUND")){

            Toast.makeText(context,"E-mail não cadastrado ainda",Toast.LENGTH_LONG).show();

        }else if(resposta.contains("12501")){

            Toast.makeText(context,"Cancelado",Toast.LENGTH_LONG).show();

        }else if(resposta.contains("7:")){

            Toast.makeText(context,"Sem conexão com a internet, verifique se sua wifi ou 3G está a funcionar",Toast.LENGTH_LONG).show();

        }else if(resposta.contains("FirebaseAuthUserCollisionException: An account already exists with the same email address")){

            Toast.makeText(context,"Existe já uma conta registrada com este email",Toast.LENGTH_LONG).show();

        }

        else{


            Toast.makeText(context,resposta,Toast.LENGTH_LONG).show();


        }

    }


    public static boolean verificarCampos (Context context, String texto_1 , String texto_2){
        if(!texto_1.isEmpty() && !texto_2.isEmpty()){
            if(verificarInternet(context)){
                return true;

            }else{
                Toast.makeText(context,"Sem conexão com a internet",Toast.LENGTH_LONG).show();
                return false;

            }
        }else{
            Toast.makeText(context,"Preencha os campos",Toast.LENGTH_LONG).show();
            return false;
        }
    }





}
