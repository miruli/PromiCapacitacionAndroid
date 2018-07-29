using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace DummyWebAPI.Models
{
    public class LoginResponse : BaseResponse<LoggedUser>
    {

    }

    public class LoggedUser
    {
        private string _userName;
        private string _password;
        private string _token;

        public string UserName { get => _userName; set => _userName = value; }
        public string Password { get => _password; set => _password = value; }
        public string Token { get => _token; set => _token = value; }
    }
}