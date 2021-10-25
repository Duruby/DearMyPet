$(document).ready(function() {
	$('#queryAllPet').click(function() {
		$.post('../queryAllPet',
			{},
			function(data) {
				data = JSON.parse(data);
				info = data.info;

				let tableTag = '<table id="all_pet" class="table" align="center">';
				tableTag += '<tr><th>ID</th><th>Color</th><th>Make</th><th>Model</th><th>Owner</th></tr>';

				info.forEach(function(pet) {
					console.log(pet);
					tableTag += '<tr><td>' + pet.Key + '</td><td>' + pet.Record.name + '</td><td>' + pet.Record.owner + '</td><td>' + pet.Record.gender + '</td><td>' + pet.Record.breed+ '</td><td>' + pet.Record.birth + '</td></tr>';
				});
				tableTag += '</table>';
				$('#all_pet').html(tableTag);
			}
		);
	});

	$('#querySubmit').click(function() {
		let petNumber = $('#queryName').val()
		alert(petNumber);
		$.post('../querySubmit',
			{ petNumber },
			function(data) {
				data = JSON.parse(data);
				pet = data.info;
				console.log(pet);
				let tableTag = '<td>' + pet.name + '</td><td>' + pet.owner + '</td><td>' + pet.gender + '</td><td>' + pet.breed+ '</td><td>' + pet.birth + '</td>';
				$('#query_pet').html(tableTag);
			}
		);
	});
	$('#createSubmit').click(function() {
		const pet_name = $('#pet_name').val();
		const pet_owner = $('#pet_owner').val();
		const pet_gender = $('#pet_gender').val();
		const pet_breed = $('#pet_breed').val();
		const pet_birth = $('#pet_birth').val();
		$.post('../createPet',
			{pet_name, pet_owner, pet_gender, pet_breed, pet_birth},
			function(data) {
				console.log(data);
				alert("ok")
			}
		);
	});

	$('#transferSubmit').click(function() {
		const pet_id2 = $('#pet_id2').val();
		const pet_owner2 = $('#pet_owner2').val();
		alert(pet_id2+" : "+pet_owner2);
		$.post('../transferSubmit',
			{ pet_id2, pet_owner2},
			function(data) {
				console.log(data);
				alert("ok")
			}
		);
	});
	
	/*function drawMenuList(mMenu) {
	    var menu = mMenu;
	    var menuHtml = "";
	    for (var i = 0; i < menu.length; i++) {
	    	menuHtml+=
	    		"<ul>"+menu[i].foodName +" "+menu[i].foodPrice +'</ul>';
	    }
	    document.getElementById("menuList").innerHTML = menuHtml;
	  }*/
	

});