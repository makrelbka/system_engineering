<template>
  <router-view />
</template>

<script setup>
import { ref, provide } from 'vue'

const cart = ref([])

provide('cart', cart)
</script>
