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
      this.$router.replace({
        path: "/main"
      });
    } else {
      this.$router.replace({
        path: "/login"
      });
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