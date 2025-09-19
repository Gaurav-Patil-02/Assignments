const canvas = document.getElementById("canvas");
const ctx = canvas.getContext("2d");

let arrowPos = [600, 600, 600, 600];
let hitStates = [false, false, false, false];

document.getElementById("reset").addEventListener("click", () => {
    arrowPos = [600, 600, 600, 600];
    hitStates = [false, false, false, false];
    draw();
});

const colors = ["yellow", "blue", "maroon", "green"];
const circleX = 100;
const radius = 40;
const arrowLength = 80;

function draw() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);

  for (let i = 0; i < 4; i++) {
    const ypos = 100 + i * 100;

    // circle
    ctx.beginPath();
    ctx.arc(circleX, ypos, radius, 0, 2 * Math.PI);
    ctx.fillStyle = hitStates[i] ? "grey" : colors[i];
    ctx.fill();
    ctx.stroke();

    // arrow line
    const tailX = arrowPos[i];
    const tipX = tailX - arrowLength;

    ctx.beginPath();
    ctx.moveTo(tailX, ypos);
    ctx.lineTo(tipX, ypos);
    ctx.strokeStyle = "black";
    ctx.lineWidth = 3;
    ctx.stroke();

    // arrow head
    const head = 12;
    const angle = Math.atan2(0, tipX - tailX);

    ctx.beginPath();
    ctx.moveTo(tipX, ypos);
    ctx.lineTo(
      tipX - head * Math.cos(angle - Math.PI / 6),
      ypos - head * Math.sin(angle - Math.PI / 6)
    );
    ctx.lineTo(
      tipX - head * Math.cos(angle + Math.PI / 6),
      ypos - head * Math.sin(angle + Math.PI / 6)
    );
    ctx.closePath();
    ctx.fillStyle = "black";
    ctx.fill();
  }
}

draw();

canvas.addEventListener("click", (e) => {
  const rect = canvas.getBoundingClientRect();
  const mouseX = e.clientX - rect.left;
  const mouseY = e.clientY - rect.top;

  for (let i = 0; i < 4; i++) {
    const ypos = 100 + i * 100;
    const dist = Math.sqrt((mouseX - circleX) ** 2 + (mouseY - ypos) ** 2);
    if (dist <= radius && !hitStates[i]) {
      animateArrow(i);
    }
  }
});

function animateArrow(i) {
  function step() {
    // stop when arrow touches circle
    if (arrowPos[i] - arrowLength > circleX + radius) {
      arrowPos[i] -= 5;  //move arrow towards circle
      draw();
      requestAnimationFrame(step);
    } else {
      hitStates[i] = true;
      draw();
    }
  }
  step();
}
