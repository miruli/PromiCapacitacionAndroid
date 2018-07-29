using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace DummyWebAPI.Models
{
    public abstract class BaseResponse<T>
    {
        private int _code;
        private string _message;
        private T _data;

        public int Code { get => _code; set => _code = value; }
        public string Message { get => _message; set => _message = value; }
        public T Data { get => _data; set => _data = value; }
    }
}