<template>
  <button
    :class="buttonClasses"
    :type="type"
    :disabled="disabled"
    @click="$emit('click', $event)"
  >
    <slot />
  </button>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  variant: {
    type: String,
    default: 'default',
    validator: (value) => ['default', 'outline', 'ghost', 'destructive'].includes(value)
  },
  size: {
    type: String,
    default: 'default',
    validator: (value) => ['default', 'sm', 'lg', 'icon'].includes(value)
  },
  type: {
    type: String,
    default: 'button'
  },
  disabled: {
    type: Boolean,
    default: false
  }
})

defineEmits(['click'])

const buttonClasses = computed(() => {
  const base = 'inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-md text-sm font-medium transition-all disabled:pointer-events-none disabled:opacity-50 outline-none focus-visible:ring-2 focus-visible:ring-black focus-visible:ring-offset-2'

  const variants = {
    default: 'bg-black text-white hover:bg-gray-800',
    outline: 'border-2 border-black bg-white text-black hover:bg-gray-50',
    ghost: 'hover:bg-gray-100 text-black',
    destructive: 'bg-red-500 text-white hover:bg-red-600'
  }

  const sizes = {
    default: 'h-9 px-4 py-2',
    sm: 'h-8 px-3 text-sm',
    lg: 'h-10 px-6',
    icon: 'h-9 w-9'
  }

  return `${base} ${variants[props.variant]} ${sizes[props.size]}`
})
</script>
