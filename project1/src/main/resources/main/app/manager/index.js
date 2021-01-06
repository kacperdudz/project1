const reimbContainer = document.querySelector('#reimbs');
let reimbs = [];

var arr = [];



const fetchAndUpdateArr = async () => { //1
    let reimbResp = await fetch('http://localhost:8080/app/api/reimb/');
    //console.log(reimbResp)
    let result = await reimbResp.json();
    arr = result;
    //console.log("body" + arr.body)
    //renderReimb(arr);// to 2
    //applyListeners();// to 3
};

fetchAndUpdateArr().then(response => renderReimb(arr)).then(response => applyListeners())

function renderReimb (tarr){ //2
     reimbContainer.innerHTML = '';
     //console.log(tarr.body);

     if (typeof tarr.body !== 'undefined'){
      for(let item of tarr.body) {

        let TRcontainer = document.createElement("tr");

        let classToGive = ''
        switch (item.status){
          case 'approved':
            classToGive = 'row-green';
            break;
          case 'denied':
            classToGive = 'row-red';
            break;
          default:
            classToGive = 'row-yellow';
        }
        TRcontainer.className = classToGive;

        //formatting ew
        item.timeCreated.minute = toMM(item.timeCreated.minute);
        
        if (item.manager == null){

            TRcontainer.innerHTML = `<td id=${item.id}>-</td>
            <td>${item.employee.fName}</td>
            <td class="statuses" title=${item.id}>${item.status}</td>
            <td>${item.title}</td>
            <td>${item.desc}</td>
            <td>${item.amt}</td>
            <td>${item.timeCreated.monthValue}/${item.timeCreated.dayOfMonth}/${item.timeCreated.year} @ ${item.timeCreated.hour}:${item.timeCreated.minute}</td>
            <td>-</td>`;

        }
        else {

            TRcontainer.innerHTML = `<td id=${item.id}>${item.manager.fName}</td>
            <td>${item.employee.fName}</td>
            <td class="statuses" title=${item.id}>${item.status}</td>
            <td>${item.title}</td>
            <td>${item.desc}</td>
            <td>${item.amt}</td>
            <td>${item.timeCreated.monthValue}/${item.timeCreated.dayOfMonth}/${item.timeCreated.year} @ ${item.timeCreated.hour}:${item.timeCreated.minute}</td>
            <td>${item.timeResolved.monthValue}/${item.timeResolved.dayOfMonth}/${item.timeResolved.year} @ ${item.timeResolved.hour}:${item.timeCreated.minute}</td>`;

        }

        //<td class="statuses" title=${item.id}><a class="statuses" data-toggle="modal" data-target="#myModal">${item.status}</a></td>

        reimbContainer.appendChild(TRcontainer);
      }
    }
 }

const applyListeners = async => { //3
  document.querySelectorAll('.statuses').forEach(item => {
    item.addEventListener('click', event => {

      //ugly flip-flop logic
      if (item.innerHTML == 'pending')
      item.innerHTML = 'denied'
      else if (item.innerHTML == 'denied')
      item.innerHTML = 'approved'
      else 
      item.innerHTML = 'denied'
      
      updateReimbDB(item.title); // to 4

      getManager(item.title); //update of manager name


    })

    //if you're reading this, please don't judge this innefficient and ugly setup of listeners :___(
    item.addEventListener('mouseenter', event => {
          //ugly flip-flop logic
          if (item.innerHTML == 'pending')
          item.style.background = 'linear-gradient(to right, #fcef8b 80%, #ff937d 80%)'
          else if (item.innerHTML == 'denied')
          item.style.background = 'linear-gradient(to right, #ff937d 80%, #8eff7d 80%)'
          else 
          item.style.background = 'linear-gradient(to right, #8eff7d 80%, #ff937d 80%)'

          //console.log("here")
    })
    item.addEventListener('click', event => {
        //ugly flip-flop logic
        if (item.innerHTML == 'pending')
        item.style.background = 'linear-gradient(to right, #fcef8b 80%, #ff937d 80%)'
        else if (item.innerHTML == 'denied')
        item.style.background = 'linear-gradient(to right, #ff937d 80%, #8eff7d 80%)'
        else 
        item.style.background = 'linear-gradient(to right, #8eff7d 80%, #ff937d 80%)'

        //console.log("here")
    })

    item.addEventListener('mouseleave', event => {
      item.style.background = ''

      //console.log("here")
})
  })
}



const updateReimbDB = async (id) => { //4
  let URL = '/app/api/updatereimb/' + id;
  const resp = await fetch(URL, {method: 'POST'});

  //console.log(URL);
  //console.log(resp);
};



const getManager = async (containerID) => {
   let URL = '/app/api/getUser';
   const resp = await fetch(URL, {method: 'GET'});

  const result = await resp.json();

  //console.log(result);

  document.getElementById(containerID).innerHTML = result.body.fName

  var parent = document.getElementById(containerID).parentElement
  var lastC = parent.lastElementChild

  if (lastC.innerHTML == '-')
      lastC.innerHTML = "NOW"

};




  //will happen initially when page is loaded
 
  ////console.log(arr)
  // renderReimb(arr);
  // applyListeners();


  if (document.querySelector('input[name="options"]')) {
    document.querySelectorAll('input[name="options"]').forEach((elem) => {
      elem.addEventListener("change", function(event) {
        var item = event.target.value;
        //console.log("button checked, value= " + item);
        filterAndDisplay(item);
      });
    });
  }


  //text input listeners
  function logSubmit(event) {
    var item = event.target.firstElementChild.value;
    //console.log("button checked, value= " + item);
    filterAndDisplay(item)
    event.preventDefault();

    document.querySelectorAll('input[name="options"]').forEach((elem) => {
        elem.checked = false;
    });

  }
  
  const form = document.getElementById('form');
  form.addEventListener('submit', logSubmit);




//used by radio buttons and input
function filterAndDisplay(statusFilter) {
  if (statusFilter == ''){
    fetchAndUpdateArr().then(response => renderReimb(arr)).then(response => applyListeners())
    // renderReimb(arr);
    // applyListeners();
    return;
  }

  fetchAndUpdateArr().then(response => {
    var temparr = JSON.parse(JSON.stringify(arr));
    temparrbody = temparr.body;
    if ((statusFilter != 'approved') && (statusFilter != 'denied') && (statusFilter != 'pending')){
      //console.log('yo')
      temparrbody = temparrbody.filter(temparrbody => temparrbody.employee.fName == statusFilter);
    }
    else{
      temparrbody = temparrbody.filter(temparrbody => temparrbody.status == statusFilter);
    }
    
    temparr.body = temparrbody;
 
    renderReimb(temparr);
  }).then(response => applyListeners())
}

//stupid formatting
function toMM(minutes) {
  if (minutes < 10) {minutes = "0"+minutes;}
  return minutes;
}