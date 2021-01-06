////console.log("working");

const reimbContainer = document.querySelector('#reimbs');
let reimbs = [];

var arr = [];

const doThing = async () => {
    //const reimbResp = await fetch('http://localhost:8080/app/api/reimb');
    const reimbResp = await fetch('http://localhost:8080/app/api/reimbByID');
    const result = await reimbResp.json();
    arr = result;
    renderReimb(arr);
};



doThing();


function renderReimb (tarr){
     reimbContainer.innerHTML = '';
     //console.log(tarr.body);

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
            <td class="statuses" title=${item.id}>${item.status}</td>
            <td>${item.title}</td>
            <td>${item.desc}</td>
            <td>${item.amt}</td>
            <td>${item.timeCreated.monthValue}/${item.timeCreated.dayOfMonth}/${item.timeCreated.year} @ ${item.timeCreated.hour}:${item.timeCreated.minute}</td>
            <td>-</td>`;

      }
      else {
            item.timeResolved.minute = toMM(item.timeResolved.minute);
            TRcontainer.innerHTML = `<td id=${item.id}>${item.manager.fName}</td>
            <td class="statuses" title=${item.id}>${item.status}</td>
            <td>${item.title}</td>
            <td>${item.desc}</td>
            <td>${item.amt}</td>
            <td>${item.timeCreated.monthValue}/${item.timeCreated.dayOfMonth}/${item.timeCreated.year} @ ${item.timeCreated.hour}:${item.timeCreated.minute}</td>
            <td>${item.timeResolved.monthValue}/${item.timeResolved.dayOfMonth}/${item.timeResolved.year} @ ${item.timeResolved.hour}:${item.timeCreated.minute}</td>`;
            

      }

      
       reimbContainer.appendChild(TRcontainer);
     }
 }



  if (document.querySelector('input[name="options"]')) {
    document.querySelectorAll('input[name="options"]').forEach((elem) => {
      elem.addEventListener("change", function(event) {
        var item = event.target.value;
        ////console.log("button checked, value= " + item);
        filterAndDisplay(item);
      });
    });
  }

function filterAndDisplay(statusFilter) {
  if (statusFilter == ''){
    renderReimb(arr);
    return;
  }
  var temparr = JSON.parse(JSON.stringify(arr));
     temparrbody = temparr.body;
     temparrbody = temparrbody.filter(temparrbody => temparrbody.status == statusFilter);
       temparr.body = temparrbody;

       renderReimb(temparr);
}


//stupid formatting
function toMM(minutes) {
  if (minutes < 10) {minutes = "0"+minutes;}
  return minutes;
}