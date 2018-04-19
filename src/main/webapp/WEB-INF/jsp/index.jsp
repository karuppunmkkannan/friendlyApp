<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
	
<style>
.body{
width:100%;
height:100%;
}

.container {
    border: 2px solid #dedede;
    background-color: #f1f1f1;
    border-radius: 5px;
    padding: 10px;
    margin: 10px 0;
}

.darker {
    border-color: #ccc;
    background-color: #ddd;
}

.container::after {
    content: "";
    clear: both;
    display: table;
}

.container img {
    float: left;
    max-width: 60px;
    width: 100%;
    margin-right: 20px;
    border-radius: 50%;
}

.container img.right {
    float: right;
    margin-left: 20px;
    margin-right:0;
}

.time-right {
    float: right;
    color: #aaa;
}

.time-left {
    float: left;
    color: #999;
}
</style>

</head>
<body>

   <div style="padding:5px">
       <input type="text" id="datamsg" />
	   <button id="btn">SEND</button>
   </div>

   <div id="msgBox" style="overflow:scroll; height:400px;width:400px;border:1px solid black">
     
   </div>

 
   
	<script type="text/javascript">
			$(document)
				.ready(
						function() {
						
			$(document).keypress(function(event){
	
	var keycode = (event.keyCode ? event.keyCode : event.which);
	if(keycode == '13'){
		send();
	}
	
		});
						
						function send(){
						
						var d = $("#datamsg").val();
								
								ws.send("/chat/msg", {}, JSON.stringify({
									'msg' : d
								}));
								
								$("#datamsg").val('');
						}
						
			$(document).on("click", "#btn", function(event) {
								
								send();
								
							});			
	
		if ("WebSocket" in window) {
			console.log("WebSocket is supported by your Browser!");

			// Let us open a web socket
			//	var socket = new SockJS("http://localhost:8080/friendly/friendlyws");
			var socket = new WebSocket("ws://192.168.1.109:8080/friendly/friendlyws");
			ws = Stomp.over(socket);

			ws.connect({}, function(frame) {
				console.log("Connected ====" + frame);

				ws.subscribe("/errors", function(message) {
					alert("Error " + message.body);
				});

				ws.subscribe("/chat/msg", function(message) {
					var g = JSON.parse(message.body);
					var dat = "<div class='container'> <img src='loginicon.png' alt='Avatar' style='width:100%;'> <p>"+g.msg+"</p><span class='time-right'>"+new Date()+"</span></div>";
					$("#msgBox").append(dat);
				});

			}, function(error) {
				console.log("STOMP error " + error);
			});

		}

		else {
			// The browser doesn't support WebSocket
			console.log("WebSocket NOT supported by your Browser!");
		}
		
		});
	</script>
</body>
</html>