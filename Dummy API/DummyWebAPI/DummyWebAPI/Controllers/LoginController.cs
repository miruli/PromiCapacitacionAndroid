using DummyWebAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace DummyWebAPI.Controllers
{
    public class LoginController : ApiController
    {
        public LoginResponse Get(string userName, string password)
        {
            var res = new LoginResponse();
            if (userName.Equals(password))
            {
                res.Code = 0;
                res.Message = @"ok";
                res.Data = new LoggedUser()
                {
                    UserName = userName,
                    Password = password,
                    Token = Guid.NewGuid().ToString()
                };
                return res;
            }
            else
            {
                res.Code = 1;
                res.Message = @"error en credenciales";
                res.Data = new LoggedUser()
                {
                    UserName = userName
                };
                return res;
            }
        }
    }
}
