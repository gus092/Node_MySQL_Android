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

var app = express();
app.use(bodyParser.json());
// app.use(bodyParser.urlencoded({extended:true}));


//Get All PERSON from database
app.get("/person",(req,res,next)=>{
  console.log("/person 진입");
  con.query('SELECT * FROM record',function(error,result,fields){
    con.on('error',function(err){
      console.log('[MYSQL]ERROR',err);
    });
    // if(result && result.length){
    //   res.end(JSON.stringify(result));
    // }else{
    //   res.end(JSON.stringify('NO person here'));
    // }
   res.end(JSON.stringify(result));

  });
});

app.post('/post', (req, res) => {
   console.log('who get in here post /users');
   var inputData;

   req.on('data', (data) => {
     inputData = JSON.parse(data);
   });

   req.on('end', () => {
     console.log("user_id : "+inputData.user_id + " , name : "+inputData.name);
     var query = "SELECT * FROM person WHERE name LIKE '%"+inputData.name+"%'";

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
});

/* 튜토리얼 post
app.post("/search",(req,res,next)=>{

  var post_data = req.body; //GET POST body
  var name_search = post_data.search;//GET field 'search' from post data
  console.log(name_search);
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
*/
app.post('/insert',(req,res)=>{
  console.log("Here in the /insert");

  req.on('data', (data) => {
    inputData = JSON.parse(data);
  });
  req.on('end', () => {
    console.log("name : "+inputData.name + " , address : "+inputData.address+ " , email : "+inputData.email +",phone" + inputData.phone);
    var insertParams = [inputData.name,inputData.address,inputData.email,inputData.phone];
    var query = 'INSERT INTO person (name,address,email,phone) VALUES (?,?,?,?)';
    con.query(query,insertParams,function(err,rows,fields){
      if(err){
        console.log(err);
      }else{
        console.log("INSERT OK");
        console.log(rows.insertId);
      }
    });
  });

});


app.listen(3306, () => {
  console.log('Example app listening on port 3306!!');
});
