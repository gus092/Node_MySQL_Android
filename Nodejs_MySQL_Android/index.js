/*
RESTFUL Services by NodeJS
Author: hyunji
MY SQL SEARCH
*/
/*
const express = require('express');
const app = express();
let users = [
  {
    id: 1,
    name: 'alice'
  },
  {
    id: 2,
    name: 'bek'
  },
  {
    id: 3,
    name: 'chris'
  }
]

//단순히 http://localhost:3000/users로 접근하면 users라는 객체를 json으로 response하라는 의미입니다.
app.get('/person', (req, res) => {
   console.log('who get in here/users');
   res.json(users)
});

app.listen(3306, () => {
  console.log('Example app listening on port 3000!');
});
*/

var express = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser');

//Connect to MYSQL
var con = mysql.createConnection({
  host : 'localhost',
  user : 'root',
  password : '290sug',//Default of XAMPP
  database : 'edmstsearch' //이름 변경하기
});

//CREATE RESTFUL
var app = express();
var publicDir = (__dirname+'/public/');
app.use(express.static(publicDir));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:true}));


//Get All PERSON from database
app.get("/person",(req,res,next)=>{
  console.log("/person 진입");
  con.query('SELECT * FROM person',function(error,result,fields){
    con.on('error',function(err){
      console.log('[MYSQL]ERROR',err);
    });
    if(result && result.length){
      res.end(JSON.stringify(result));
    }else{
      res.end(JSON.stringify('NO person here'));
    }
  });
});

//SEARCH PERSON ny name
app.post("/search",(req,res,next)=>{

  var post_data = req.body; //GET POST body
  var name_search = post_data.search;//GET field 'search' from post data

  var query = "SELECT * FROM person WHERE name LIKE '%"+name_search+"%'";

  con.query(query,function(error,result,fields){
    con.on('error',function(err){
      console.log('[MYSQL]ERROR',err);
    });
    if(result && result.length){
      res.end(JSON.stringify(result));
    }else{
      res.end(JSON.stringify('NO person here'));
    }
  });
});

//Start SERVER
app.listen(3306,()=>{
  console.log('EDMTDev SEARCH API running on port  3306');
})
