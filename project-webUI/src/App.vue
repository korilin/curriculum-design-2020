<template>
  <div id="app">
    <transition mode="out-in" name="loginT" appear>
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>
export default {
  name: "",
  data() {
    return {};
  },
  components: {},
  created() {
    if (localStorage.getItem("access_token")) {
      this.axios
        .get("/baseInfo?access_token=" + localStorage.getItem("access_token"))
        .then(response => {
          if (response.data.status == 200) {
            if (this.$route.name != "Main") {
              this.$router.replace({
                name: "Main",
                params: {
                  userType: response.data.userType,
                  userName: response.data.name
                }
              });
            }
          } else if (response.data.status == 302) {
            this.$Message.error(response.data.errorMessage);
            localStorage.removeItem("access_token");
            this.$router.replace({ name: "Login" });
          }
        })
        .catch(error => {
          this.$Message.error(error);
        });
    } else {
      if (this.$route.path != "/login") {
        this.$router.replace({ name: "Login" });
      }
    }
  }
};
</script>

<style>
.loginT-enter-active {
  transition: opacity 0.5s;
}
.loginT-leave-active {
  transition: opacity 0.3s;
}
.loginT-enter,
.loginT-leave-to {
  opacity: 0;
}
</style>