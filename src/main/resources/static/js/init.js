(function($){
  $(function(){

    $('.sidenav').sidenav();
    $('.parallax').parallax();
    $('.dropdown-trigger').dropdown({coverTrigger: false, constrainWidth: false});
    $('.collapsible').collapsible();
    $('.modal').modal();
    $('select').formSelect();

  }); // end of document ready
})(jQuery); // end of jQuery name space


var PasswordInput = document.querySelector('#password');
var submit = document.querySelector('#submit');

/*
I'm using this IssueTracker to help me format my validation messages.
 */
function IssueTracker() {
  this.issues = [];
}
IssueTracker.prototype = {
  add: function (issue) {
    this.issues.push(issue);
  },
  retrieve: function () {
    var message = "";
    switch (this.issues.length) {
      case 0:
        // do nothing because message is already ""
        break;
      case 1:
        message = "Please correct the following issue:\n" + this.issues[0];
        break;
      default:
        message = "Please correct the following issues:\n" + this.issues.join("\n");
        break;
    }
    return message;
  }
};

submit.onclick = function () {

  var userPassword = PasswordInput.value;

  var inputIssuesTracker = new IssueTracker();
  /*
  This steps through all of the requirements and adds messages when a requirement fails.
  Just checks the first password because the second should be the same when it runs.
   */
  function checkRequirements() {
    if (userPassword.length < 8) {
      inputIssuesTracker.add("Password should be more than 8 characters");
    } else if (userPassword.length > 100) {
      inputIssuesTracker.add("Password should be less than 100 characters");
    }

    if (!userPassword.match(/[\!\@\#\$\%\^\&\*\.]/g)) {
      inputIssuesTracker.add("missing a symbol (!, @, #, $, %, ^, &, ., *)");
    }

    if (!userPassword.match(/\d/g)) {
      inputIssuesTracker.add("missing a number");
    }

    if (!userPassword.match(/[a-z]/g)) {
      inputIssuesTracker.add("missing a lowercase letter");
    }

    if (!userPassword.match(/[A-Z]/g)) {
      inputIssuesTracker.add("missing an uppercase letter");
    }

    var illegalCharacterGroup = userPassword.match(/[^A-z0-9\!\@\#\$\%\^\&\*\.]/g)
    if (illegalCharacterGroup) {
      illegalCharacterGroup.forEach(function (illegalChar) {
        inputIssuesTracker.add("includes illegal character: " + illegalChar);
      });
    }
  };

  checkRequirements();

  var inputIssues = inputIssuesTracker.retrieve()

  /*
  Let input.setCustomValidity() do its magic :)
   */
  PasswordInput.setCustomValidity(inputIssues);
  /*
  You would probably replace this with a POST message in a real app.
   */
  /*
  if (inputIssues.length === 0) {
    alert("Password change successful!");
  }
  */

};